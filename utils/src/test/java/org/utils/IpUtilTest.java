/*package org.utils;

import static org.junit.Assert.*;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;  

import com.jngld.utils.IpUtil;


public class IpUtilTest{
	
	@Test
	public void testGetIpAddress() {
		String setIp = "157.235.15.57";
		simpleTestHeader("x-forwarded-for", setIp);
		simpleTestHeader("Proxy-Client-IP", setIp);
		simpleTestHeader("X-Forwarded-For", setIp);
		simpleTestHeader("WL-Proxy-Client-IP", setIp);
		simpleTestHeader("X-Real-IP", setIp);
		//RemoteAddr
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr(setIp);
		String ip = IpUtil.getIpAddress(request);
		assertEquals(ip, setIp);
		System.out.println("----------");
		request  = new MockHttpServletRequest();
		int i = 0;
		request.setRemoteAddr(++i + "");
		request.addHeader("X-Real-IP", ++i + "");
		request.addHeader("WL-Proxy-Client-IP", ++i + "");
		request.addHeader("Proxy-Client-IP", ++i + "");
		request.addHeader("x-forwarded-for", ++i + "");
		ip = IpUtil.getIpAddress(request);
		assertEquals(ip, i+"");
	}
	
	private void simpleTestHeader(String key, String value) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(key, value);
		String ip = IpUtil.getIpAddress(request);
		assertEquals(ip, value);
	}

}
*/