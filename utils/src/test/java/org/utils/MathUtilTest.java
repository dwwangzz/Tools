package org.utils;

import java.io.IOException;

import com.jngld.utils.IpUtil;
import com.jngld.utils.MathUtil;
import com.jngld.utils.entity.IpInfo;

import junit.framework.TestCase;

public class MathUtilTest {
	
	public void test1() throws IOException{
		System.out.println(2134234);
		
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 220000; i++) {
			try {
				IpInfo info = IpUtil.getIpAllInfo("43.241.221.158");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(i);
			}
		}
		 try {
			Thread.currentThread().sleep(600000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
