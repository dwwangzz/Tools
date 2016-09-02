package com.jngld.mqutil;


import com.jngld.mqutil.exception.MessageException;

public interface MessageDao {

  final static int TYPE_POINT = 1;
  final static int TYPE_TOPIC = 2;
  final static String TYPE_POINT_PRE= "ponit_get_";
  final static String TYPE_TOPIC_PRE= "topic_get_";
  /**
   * 
   * 发送消息
   * @author xus-a
   * @date 2015年12月8日 上午9:23:02 
   * @param connUrl      mq的链接地址
   * @param queueName    队列名称
   * @param content      发送内容
   * @param DeliveryMode 是否持久化
   * @throws MessageException
   */
  public void sendPonitMesaage(String queueName,String content,boolean DeliveryMode) throws MessageException ;
  /**
   * 
   * 接收消息
   * @author xus-a
   * @date 2015年12月8日 上午9:24:28 
   * @param connUrl     mq的链接地址
   * @param queueName   队列或者订阅名称
   * @return            接收到的消息
   * @throws MessageException
   */
  public void getPonitMessage(String queueName, IAction action) throws MessageException;
  /**
   * 
   * 发送订阅消息
   * @author xus-a
   * @date 2015年12月8日 上午9:25:03 
   * @param connUrl    mq的链接地址
   * @param queueName  订阅名称
   * @param content    内容
   * @param DeliveryMode   是否持久化消息
   * @throws MessageException
   */
  public void sendTopicMessage(String queueName,String content,boolean DeliveryMode) throws MessageException;
  /**
   * 
   * 接收订阅消息
   * @author xus-a
   * @date 2015年12月8日 上午9:26:28 
   * @param connUrl    mq的链接地址
   * @param topicName  订阅名称
   * @return           获得的订阅信息
   * @throws MessageException
   */
  public void getTopicMessage(String topicName, IAction action) throws MessageException;
}
