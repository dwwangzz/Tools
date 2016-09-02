package com.jngld.utils.entity;

public class ReturnEntity {

    private String message;

    private int status;

    private Object retData;

    public ReturnEntity() {
    }

    public ReturnEntity(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ReturnEntity(int status, String message, Object retData) {
        this.status = status;
        this.message = message;
        this.retData = retData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "ReturnEntity [message=" + message + ", status=" + status
                + ", retData=" + retData + "]";
    }

}
