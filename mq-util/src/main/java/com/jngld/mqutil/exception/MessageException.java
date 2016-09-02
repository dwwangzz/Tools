package com.jngld.mqutil.exception;

public class MessageException extends Exception {

  /** 变量意义 */
  private static final long serialVersionUID = -7005825719751338877L;

  public MessageException() {
    super();
  }

  public MessageException(String message) {
    super(message);
  }

  public MessageException(Throwable cause) {
    super(cause);
  }

  public MessageException(String message, Throwable cause) {
    super(message, cause);
  }
}
