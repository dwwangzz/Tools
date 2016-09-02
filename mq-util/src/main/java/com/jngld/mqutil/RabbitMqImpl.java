package com.jngld.mqutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.jngld.mqutil.entity.RabbitMqInfo;
import com.jngld.mqutil.exception.MessageException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitMqImpl implements MessageDao {
  private Map<String, RabbitMqInfo> threadMap;
  private String connUrl;

  public RabbitMqImpl(String connUrl) throws MessageException {
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
      boolean DeliveryMode) throws MessageException {
    // 开启连接
    Channel channel = null;
    Connection connection = null;
    try {
      connection = getConn(connUrl);
      channel = sendMessage(connection, queueName, content, DeliveryMode,
          TYPE_POINT);
    } finally {
      if (channel != null) {
        try {
          channel.close();
        } catch (IOException e) {
          throw new MessageException(e);
        } catch (TimeoutException e) {
          throw new MessageException(e);
        }
      }
      if (connection != null) {
        try {
          connection.close();
        } catch (IOException e) {
          throw new MessageException(e);
        }
      }
    }
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
      boolean DeliveryMode) throws MessageException {
    // 开启连接
    Channel channel = null;
    Connection connection = null;
    try {
      connection = getConn(connUrl);
      channel = sendMessage(connection, queueName, content, DeliveryMode,
          TYPE_TOPIC);
    } finally {
      if (channel != null) {
        try {
          channel.close();
        } catch (IOException e) {
          throw new MessageException(e);
        } catch (TimeoutException e) {
          throw new MessageException(e);
        }
      }
      if (connection != null) {
        try {
          connection.close();
        } catch (IOException e) {
          throw new MessageException(e);
        }
      }
    }
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
    Connection connection = getConn(connUrl);
    getMessage(connection, queueName, TYPE_POINT, action);
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
  public void getTopicMessage(String topicName, IAction action)
      throws MessageException {
    Connection connection = getConn(connUrl);
    getMessage(connection, topicName, TYPE_TOPIC, action);
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
  private Channel sendMessage(Connection conn, String queueName, String content,
      boolean DeliveryMode, int msgType) throws MessageException {
    Channel channel;
    try {
      channel = conn.createChannel();
      // 配置连接
      if (msgType == TYPE_POINT) {
        channel.queueDeclare(queueName, DeliveryMode, false, false, null);
        channel.basicPublish("", queueName.toString(), null,
            content.getBytes());
      }
      if (msgType == TYPE_TOPIC) {
        channel.exchangeDeclare(queueName, "topic");
        channel.basicPublish(queueName, UUID.randomUUID().toString(), null,
            content.getBytes());
      }
    } catch (IOException e) {
      throw new MessageException(e);
    }
    return channel;
  }

  /**
   * 
   * 获得消息
   * 
   * @author xus-a
   * @date 2015年12月8日 上午10:28:07
   * @param conn
   *          链接
   * @param queueName
   *          队列名称
   * @param msgType
   *          消息类型
   * @return String 消息内容
   * @throws MessageException
   */
  private void getMessage(Connection conn, String queueName, int msgType,
      final IAction action) throws MessageException {
    Channel channel = null;
    final QueueingConsumer consumer = new QueueingConsumer(channel);
    try {
      channel = conn.createChannel();
      if (msgType == TYPE_POINT) {
        // 配置连接
        channel.queueDeclare(queueName, false, false, false, null);

        channel.basicConsume(queueName, true, consumer);
      }

      if (msgType == TYPE_TOPIC) {
        // 声明转发器
        channel.exchangeDeclare(queueName, "topic");
        // 随机生成一个队列
        String randqueue = channel.queueDeclare().getQueue();
        channel.queueBind(randqueue, queueName, "*");
        // 指定消费队列
        channel.basicConsume(randqueue, true, consumer);
      }
      Thread t = new Thread(new Runnable() {
        public void run() {
          while (true) {
            String result = "";
            QueueingConsumer.Delivery delivery = null;
            try {
              delivery = consumer.nextDelivery();
            } catch (ShutdownSignalException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (ConsumerCancelledException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            if (delivery != null) {
              result = new String(delivery.getBody());
              action.exec(result);
            } else {
              result = "";
            }
          }
        }
      });
      t.start();
      // 缓存线程
      if (threadMap == null) {
        threadMap = new HashMap<String, RabbitMqInfo>();
      }
      RabbitMqInfo rm = new RabbitMqInfo();
      rm.setConnection(conn);
      rm.setThread(t);
      if (msgType == TYPE_POINT) {
        threadMap.put(TYPE_POINT_PRE + queueName, rm);
      }
      if (msgType == TYPE_TOPIC) {
        threadMap.put(TYPE_TOPIC_PRE + queueName, rm);
      }

    } catch (IOException e) {
      throw new MessageException(e);
    }
  }

  /**
   * 
   * 获取链接
   * 
   * @author xus-a
   * @date 2015年12月8日 上午10:29:07
   * @param connUrl
   *          链接地址
   * @return 链接
   * @throws MessageException
   */
  private Connection getConn(String connUrl) throws MessageException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(connUrl);
    Connection connection;
    try {
      connection = factory.newConnection();
    } catch (IOException e) {
      throw new MessageException(e);
    } catch (TimeoutException e) {
      throw new MessageException(e);
    }
    return connection;
  }

  /*
   * private Connection getConnection(String queueName) throws MessageException{
   * Connection connection = null;
   * if(connMap!=null&&connMap.containsKey(queueName)){ connection =
   * connMap.get(queueName); }else{ if(connMap==null){ connMap = new
   * HashMap<String, Connection>(); } connection = getConn(connUrl);
   * connMap.put(queueName,connection); } return connection; }
   */

  public void closeTopic(String queueName) throws IOException {
    closeConnection(queueName, TYPE_TOPIC);
  }

  public void closePoint(String queueName) throws IOException {
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
   */
  @SuppressWarnings("deprecation")
  private void closeConnection(String queueName, int msgType)
      throws IOException {
    if (msgType == TYPE_POINT) {
      queueName = TYPE_POINT_PRE + queueName;
    }
    if (msgType == TYPE_TOPIC) {
      queueName = TYPE_TOPIC_PRE + queueName ;
    }
    if (threadMap != null && threadMap.containsKey(queueName)) {
      RabbitMqInfo rm = threadMap.get(queueName);
      if (rm.getConnection() != null) {
        rm.getConnection().close();
      }
      rm.getThread().stop();
      threadMap.remove(queueName);
    }
  }

  public static void main(String[] args) throws Exception {
    RabbitMqImpl m = new RabbitMqImpl("localhost");
    // Thread.sleep(millis);
    // m.connection;

    m.getTopicMessage("haha", new IAction() {
      public void exec(String message) {
        System.out.println("haha" + message);
      }
    });
    m.getTopicMessage("wawa", new IAction() {
      public void exec(String message) {
        System.out.println("wawa" + message);
      }
    });

    m.getTopicMessage("enen", new IAction() {
      public void exec(String message) {
        System.out.println("enen" + message);
      }
    });

    m.closeTopic("wawa");

    // m.sendTopicMessage("enen", "dsa", false);

    // System.out.println(m.getPonitMessage("localhost", "topictest"));
  }
}
