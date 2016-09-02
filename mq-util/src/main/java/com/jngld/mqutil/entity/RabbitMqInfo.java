package com.jngld.mqutil.entity;

import com.rabbitmq.client.Connection;

public class RabbitMqInfo {
  private Connection connection;
  private Thread thread;
  public Connection getConnection() {
    return connection;
  }
  public void setConnection(Connection connection) {
    this.connection = connection;
  }
  public Thread getThread() {
    return thread;
  }
  public void setThread(Thread thread) {
    this.thread = thread;
  }
  
}
