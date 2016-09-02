package com.jngld.utils.entity;

public class IpLookUp {
	
	private int errNum;
	
	private String errMsg;
	
	private IpInfo retData = new IpInfo();

	public int getErrNum() {
		return errNum;
	}

	public void setErrNum(int errNum) {
		this.errNum = errNum;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public IpInfo getRetData() {
		return retData;
	}

	public void setRetData(IpInfo retData) {
		this.retData = retData;
	}
	
}
