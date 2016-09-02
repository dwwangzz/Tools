package com.jngld.mqutil.entity;

import javax.jms.Connection;

public class ActiveMqInfo {
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
