package com.jngld.mqutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.jngld.mqutil.exception.MessageException;

public class ActiveMqdImpl implements MessageDao {
  private Map<String, Connection> connMap;
  private String connUrl;

  public ActiveMqdImpl(String connUrl) throws MessageException {
    this.connUrl = connUrl;
  }

  /**
   * 发送点对点信息
   * 
   * @param connUrl
   *          mq的链接地址
   * @param queueName
   *          队列名称
   * @param content
   *          发送内容
   * @param DeliveryMode
   *          是否持久化
   * @throws MessageException
   * @see com.jngld.mqutil.MessageDao#sendPonitMesaage(java.lang.String,
   *      java.lang.String, java.lang.String, boolean)
   */
  public void sendPonitMesaage(String queueName, String content,
      boolean deliveryMode) throws MessageException {
    sendMessage(queueName, content, deliveryMode, TYPE_POINT);
  }

  /**
   * 接收点对点消息
   * 
   * @param connUrl
   *          mq的链接地址
   * @param queueName
   *          队列或者订阅名称
   * @return 接收到的消息
   * @throws MessageException
   * @see com.jngld.mqutil.MessageDao#getPonitMessage(java.lang.String,
   *      java.lang.String)
   */
  public void getPonitMessage(String queueName, IAction action)
      throws MessageException {
    getMessageJMS(queueName, TYPE_POINT, action);
  }

  /**
   * 
   * 发送广播
   * 
   * @param connUrl
   *          广播地址
   * @param queueName
   *          队列名
   * @param content
   *          内容
   * @param deliveryMode
   *          是否持久化
   * @throws MessageException
   * @see com.jngld.mqutil.MessageDao#sendTopicMessage(java.lang.String,
   *      java.lang.String, java.lang.String, boolean)
   */
  public void sendTopicMessage(String queueName, String content,
      boolean deliveryMode) throws MessageException {
    sendMessage(queueName, content, deliveryMode, TYPE_TOPIC);
  }

  /**
   * 
   * 获得广播消息
   * 
   * @param connUrl
   *          地址
   * @param topicName
   *          广播名称
   * @return String 广播内容
   * @throws MessageException
   * @see com.jngld.mqutil.MessageDao#getTopicMessage(java.lang.String,
   *      java.lang.String)
   */
  public void getTopicMessage(String topicName, IAction action) throws MessageException {
    getMessageJMS(topicName, TYPE_TOPIC ,action);
  }

  /**
   * 
   * 发送消息
   * 
   * @author xus-a
   * @date 2015年12月8日 上午9:59:40
   * @param connUrl
   *          链接地址
   * @param queueName
   *          队列名称
   * @param content
   *          消息内容
   * @param deliveryMode
   *          是否持久化
   * @param msgType
   *          消息类型
   * @throws MessageException
   */
  private void sendMessage(String queueName, String content,
      boolean deliveryMode, int msgType) throws MessageException {

    Session session = null;
    // Destination ：消息的目的地;消息发送给谁.
    Destination destination = null;
    // MessageProducer：消息发送者
    MessageProducer producer;
    Connection connection = null;
    try {
      connection = getConnection(connUrl);
      session = connection.createSession(Boolean.TRUE,
          Session.AUTO_ACKNOWLEDGE);
      // 获取session,创建队列
      if (msgType == TYPE_POINT) {
        destination = session.createQueue(queueName);
      }
      if (msgType == TYPE_TOPIC) {
        destination = session.createTopic(queueName);
      }
      // 得到消息生成者【发送者】
      producer = session.createProducer(destination);
      // 设置持久化(如果设置了持久化重启mq消息仍然存在)
      if (deliveryMode) {
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
      } else {
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
      }
      // 构造消息
      TextMessage message = session.createTextMessage(content);
      // 发送消息到目的地方
      producer.send(message);
      session.commit();

    } catch (JMSException e) {
      throw new MessageException(e);
    } finally {
      close(connection, session);
    }

  }

  /**
   * 返回点对点消息
   * 
   * @author xus-a
   * @date 2015年12月4日 上午10:16:48
   * @param queueName
   *          队列名称
   * @param msgType
   *          消息类型
   * @return String 消息内容
   * @throws JMSException
   */
  private void getMessageJMS(String queueName, int msgType,
      final IAction action) throws MessageException {
    // Connection ：JMS 客户端到JMS Provider 的连接
    Connection connection = null;
    // Session： 一个发送或接收消息的线程
    Session session = null;
    // Destination ：消息的目的地;消息发送给谁.
    Destination destination = null;
    // 消费者，消息接收者
    MessageConsumer consumer;
    try {
      connection = getConnection(connUrl);
      // 第一个参数开启事物
      session = connection.createSession(Boolean.FALSE,
          Session.AUTO_ACKNOWLEDGE);
      // 根据不同的消息去创建不同的
      if (msgType == TYPE_POINT) {
        destination = session.createQueue(queueName);
      }
      if (msgType == TYPE_TOPIC) {
        destination = session.createTopic(queueName);
      }
      consumer = session.createConsumer(destination);
      consumer.setMessageListener(new MessageListener() {
        public void onMessage(Message message) {
          String result = "";
          try {
            result = ((TextMessage) message).getText();
            action.exec(result);
          } catch (JMSException e) {
            e.printStackTrace();
          }
        }
      });
      // 缓存线程
      if (connMap == null) {
        connMap = new HashMap<String, Connection>();
      }
      if (msgType == TYPE_POINT) {
        connMap.put(TYPE_POINT_PRE + queueName, connection);
      }
      if (msgType == TYPE_TOPIC) {
        connMap.put(TYPE_TOPIC_PRE + queueName, connection);
      }
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * 获得链接
   * 
   * @author xus-a
   * @date 2015年12月8日 上午9:57:23
   * @param connUrl
   *          链接地址
   * @return Connection 开启的链接
   * @throws MessageException
   */
  private static Connection getConnection(String connUrl)
      throws MessageException {
    Connection connection = null;
    // ConnectionFactory ：连接工厂，JMS 用它创建连接
    ConnectionFactory connectionFactory;
    connectionFactory = new ActiveMQConnectionFactory(
        ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD,
        connUrl);
    // 构造从工厂得到连接对象
    try {
      connection = connectionFactory.createConnection();
      // 启动
      connection.start();
    } catch (JMSException e) {
      throw new MessageException(e);
    }
    return connection;
  }

  /**
   * 
   * 关闭方法
   * 
   * @author xus-a
   * @date 2015年12月8日 上午9:56:00
   * @param conn
   *          javax.jms.Connection
   * @param session
   *          javax.jms.Session
   * @throws MessageException
   */
  private void close(Connection conn, Session session) throws MessageException {
    if (session != null) {
      try {
        session.close();
      } catch (JMSException e) {
        throw new MessageException(e);
      }
    }
    if (conn != null) {
      try {
        conn.close();
      } catch (JMSException e) {
        throw new MessageException(e);
      }
    }
  }

  public void closeTopic(String queueName) throws MessageException {
    closeConnection(queueName, TYPE_TOPIC);
  }

  public void closePoint(String queueName) throws MessageException {
    closeConnection(queueName, TYPE_POINT);
  }

  /**
   * 
   * @Description 关闭队列监听
   * @author xus-a
   * @date 2015年12月10日 下午5:01:41
   * @param queueName
   *          队列名称
   * @throws IOException
   * @throws MessageException 
   */
  @SuppressWarnings("deprecation")
  private void closeConnection(String queueName, int msgType)
      throws MessageException {
    if (msgType == TYPE_POINT) {
      queueName = TYPE_POINT_PRE + queueName;
    }
    if (msgType == TYPE_TOPIC) {
      queueName = TYPE_TOPIC_PRE + queueName ;
    }
    if (connMap != null && connMap.containsKey(queueName)) {
        try {
          connMap.get(queueName).close();
        } catch (JMSException e) {
          throw new MessageException(e);
        };
      connMap.remove(queueName);
    }
  }
  
  public static void main(String[] args) throws MessageException {

    ActiveMqdImpl a = new ActiveMqdImpl("tcp://localhost:61616");

    // a.sendTopicMessage("tcp://localhost:61616", "casetopicddd", "4214214",
    // false);

    // a.closeConn();
    // System.out.println(a.getTopicMessage("tcp://localhost:61616",
    // "casetopicddd"));

    /*a.getPonitMessage("casetopicddd", new IAction() {
      public void exec(String message) {
        System.out.println("实现方法接收到" + message);
      }
    });*/
/*    a.getPonitMessage("haha", new IAction() {
      public void exec(String message) {
        System.out.println(message);
      }
    });
    a.getPonitMessage("wawa", new IAction() {
      public void exec(String message) {
        System.out.println(message);
      }
    });
    a.getPonitMessage("xxxsa", new IAction() {
      public void exec(String message) {
        System.out.println(message);
      }
    });
    
    a.closePoint("xxxsa");*/
     a.sendPonitMesaage("wawa", "xx", false);

  }
}
