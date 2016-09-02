package org.utils;

import com.jngld.utils.ConvertUtil;
import com.jngld.utils.exception.ParameterException;

import junit.framework.TestCase;

public class convertUtilTest extends TestCase{
	
	/**
	 * @Description 测试string2Double功能
	 * @author liuy-8
	 * @date 2015年11月30日 上午10:39:36 
	 */
	public void testString2Double() { 
		assertEquals(22.22, ConvertUtil.string2Double("22.22"));  
		assertEquals(-22.22, ConvertUtil.string2Double("-22.22"));  
		assertEquals(0.0, ConvertUtil.string2Double("0"));  
		assertEquals(-0.0, ConvertUtil.string2Double("-0"));  
		assertEquals(1.2, ConvertUtil.string2Double("-", 1.2));  
		assertEquals(0.0, ConvertUtil.string2Double("-"));  
		assertEquals(0.0, ConvertUtil.string2Double(null));  
	}
	
	/**
	 * @Description 测试string2Int功能
	 * @author liuy-8
	 * @date 2015年11月30日 上午10:39:50 
	 */
	public void testString2Int() {
		assertEquals(2, ConvertUtil.string2Int("2"));
		assertEquals(-2, ConvertUtil.string2Int("-2"));
		assertEquals(0, ConvertUtil.string2Int("0"));
		assertEquals(0, ConvertUtil.string2Int("-0"));
		assertEquals(-1, ConvertUtil.string2Int("2.0", -1));
		assertEquals(0, ConvertUtil.string2Int("2.0"));
		assertEquals(0, ConvertUtil.string2Int(null));
	}
	
	/**
	 * @Description 测试string2Float功能
	 * @author liuy-8
	 * @date 2015年11月30日 上午10:40:11 
	 */
	public void testString2Float() {
		assertEquals(22.22f, ConvertUtil.string2Float("22.22"));  
		assertEquals(-22.22f, ConvertUtil.string2Float("-22.22"));  
		assertEquals(0.0f, ConvertUtil.string2Float("0"));  
		assertEquals(-0.0f, ConvertUtil.string2Float("-0"));  
		assertEquals(1.2f, ConvertUtil.string2Float("-", 1.2f));  
		assertEquals(0.0f, ConvertUtil.string2Float("-"));  
		assertEquals(0.0f, ConvertUtil.string2Float(null));  
	}
	
	/**
	 * @Description 测试string2Long功能
	 * @author liuy-8
	 * @date 2015年11月30日 上午10:40:11 
	 */
	public void testString2Long() {
		assertEquals(2222222222222222L, ConvertUtil.string2Long("2222222222222222"));  
		assertEquals(-2222222222222222L, ConvertUtil.string2Long("-2222222222222222"));  
		assertEquals(0L, ConvertUtil.string2Long("0"));  
		assertEquals(-0L, ConvertUtil.string2Long("-0"));  
		assertEquals(1L, ConvertUtil.string2Long("-", 1L));  
		assertEquals(0L, ConvertUtil.string2Long("-"));
		assertEquals(0L, ConvertUtil.string2Long(null));
	}
	
	public void testHexString2Byte() {
		//逻辑不知
	}
	
	/**
	 * @Description 测试HexString2Byte抛出异常
	 * @author liuy-8
	 * @date 2015年11月30日 上午10:43:07 
	 * @throws Exception
	 */
	public void testHexString2ByteException() {
		try {
			ConvertUtil.hexString2Byte(null);
			//没抛异常
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(e instanceof ParameterException);
		}
		
		try {
			ConvertUtil.hexString2Byte("");
			//没抛异常
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(e instanceof ParameterException);
		}
	}
	
	/**
	 * @Description 测试Byte2HexString抛出异常
	 * @author liuy-8
	 * @date 2015年11月30日 上午10:43:07 
	 * @throws Exception
	 */
	public void testByte2HexStringException() {
		try {
			ConvertUtil.byte2HexString(null);
			//没抛异常
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(e instanceof ParameterException);
		}
		
		try {
			byte[] b = new byte[0];
			ConvertUtil.byte2HexString(b);
			//没抛异常
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(e instanceof ParameterException);
		}
	}

}
