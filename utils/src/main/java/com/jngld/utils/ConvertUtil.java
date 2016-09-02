package com.jngld.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.jngld.utils.exception.ParameterException;

/**
 * 
 * 数据类型转换工具类
 * @author (作者) huangqw
 * @date (开发日期) 2015年11月25日 下午2:29:15 
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class ConvertUtil {
    
    private static final String HEX = "0123456789ABCDEF";
		
	/**
	 * 
	 * 字符串转化为整型
	 * @author huangqw
	 * @param numStr 要转化的字符串
	 * @param defaultValue 默认值
	 * @returnint 
	 * @date 2015年11月25日下午2:31:26
	 * @throws Exception
	 */
	public static int string2Int(String numStr, int defaultValue) {
		int num = defaultValue;
		try {
			if (null == numStr) {
				num = defaultValue;
			} else {
				num = Integer.parseInt(numStr.trim());
			}
		} catch (Exception e) {
			num = defaultValue;
		}
		return num;
	}
	
	/**
	 * 字符串转化为整型，转化失败返回0
	 * @author huangqw
	 * @param numStr 要转化的字符串
	 * @returnint
	 * @date 2015年11月25日下午3:30:49
	 * @throws Exception
	 */
	public static int string2Int(String numStr) {
		return string2Int(numStr, 0);
	}

	
	/**
	 * 
	 * 字符串转化为float
	 * @author huangqw
	 * @param numStr 要转化的字符串
	 * @param defaultValue 默认值
	 * @returnfloat
	 * @date 2015年11月25日下午3:06:28
	 * @throws Exception
	 */
	public static float string2Float(String numStr, float defaultValue) {
		float num = defaultValue;
		try {
			if (null == numStr) {
				num = defaultValue;
			} else {
				num = Float.parseFloat(numStr.trim());
			}
		} catch (Exception e) {
			num = defaultValue;
		}
		return num;
	}
	
	/**
	 * 
	 * 字符串转化为float，转化失败返回0
	 * @author huangqw
	 * @param numStr 要转化的字符串
	 * @returnfloat
	 * @date 2015年11月25日下午3:33:22
	 * @throws Exception
	 */
	public static float string2Float(String numStr) {
		return string2Float(numStr, 0);
	}
	
	/**
	 * 
	 * 字符串转化为double
	 * @author huangqw
	 * @param numStr 要转化的字符串
	 * @param defaultValue 默认值
	 * @returndouble
	 * @date 2015年11月25日下午2:32:56
	 * @throws Exception
	 */
	public static double string2Double(String numStr, double defaultValue) {
		double num = defaultValue;
		try {
			if (null == numStr) {
				num = defaultValue;
			} else {
				num = Double.parseDouble(numStr.trim());
			}
		} catch (Exception e) {
			num = defaultValue;
		}
		return num;
	}
	
	/**
	 * 
	 * 字符串转化为double,转化失败返回0
	 * @author huangqw
	 * @param numStr 要转化的字符串
	 * @returndouble
	 * @date 2015年11月25日下午3:34:21
	 * @throws Exception
	 */
	public static double string2Double(String numStr) {
		return string2Double(numStr, 0);
	}
	
	/**
	 * 
	 * 字符串转化为long
	 * @author huangqw
	 * @param numStr 要转化的字符串
	 * @param defaultValue 默认值
	 * @returnlong
	 * @date 2015年11月25日下午2:33:23
	 * @throws Exception
	 */
	public static long string2Long(String numStr, long defaultValue) {
		long num = defaultValue;
		try {
			if (null == numStr) {
				num = defaultValue;
			} else {
				num = Long.parseLong(numStr.trim());
			}
		} catch (Exception e) {
			num = defaultValue;
		}
		return num;
	}
	
	
	/**
	 * 
	 * 字符串转化为long，转化失败返回0
	 * @author huangqw
	 * @param numStr 要转化的字符串
	 * @returnlong
	 * @date 2015年11月25日下午3:35:11
	 * @throws Exception
	 */
	public static long string2Long(String numStr) {
		return string2Long(numStr, 0);
	}
	
    
    /**
     * 
     * byte数组转16进制字符串
     * @author youps-a
     * @date 2015年11月25日 下午4:47:21
     * @param b
     * @return
     */
    public static String byte2HexString(byte[] b) {
        if (ArrayUtils.isEmpty(b)) {
           throw new ParameterException(ParameterException.message);
        } else {
        	 StringBuilder sb = new StringBuilder();
             for (int i = 0; i < b.length; i++) {
                 String s = Integer.toHexString(b[i] & 0xFF);
                 if (s.length() == 1) {
                     sb.append("0");
                 }
                 sb.append(s);
             }
             return sb.toString().toUpperCase();
        }
    }
    
    /**
     * 
     * 16进制字符串转byte数组
     * @author youps-a
     * @date 2015年11月25日 下午4:49:36
     * @param value
     * @return
     * @throws Exception 
     */
    public static byte[] hexString2Byte(String value) {
        if (StringUtils.isEmpty(value)) {
        	throw new ParameterException(ParameterException.message);
        } else {
        	 value = value.toUpperCase();
             char[] hexChars = value.toCharArray();
             byte[] b = new byte[hexChars.length / 2];
             for (int i = 0; i < b.length; i++) {
                 byte high = (byte) (HEX.indexOf(hexChars[i * 2]) << 4 & 0xF0);
                 byte low = (byte) (HEX.indexOf(hexChars[i * 2 + 1]) & 0xF);
                 b[i] = (byte) (high | low);
             }
             return b;
        }
    }
}
