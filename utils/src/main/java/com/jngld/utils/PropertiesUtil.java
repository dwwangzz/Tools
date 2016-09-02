package com.jngld.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Description 配置文件读取工具类
 * @author (作者) youps-a
 * @date (开发日期) 2015年11月25日 下午4:21:24
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class PropertiesUtil {
    
    private static final String UTF_8 = "UTF-8";

	private static final Map<String, Properties> PROPS = new HashMap<String, Properties>();

	/**私有化构造函数*/
	private PropertiesUtil() {
	}

	/**
	 * 
	 * @Description 根据name获取properties文件中的value
	 * @author youps-a
	 * @date 2015年11月26日 上午9:44:25
	 * @param filePath     properties文件路径(classpath中的相对路径)
	 * @param name         所要获取的properties文件中的属性名
	 * @return
	 * @throws IOException 
	 */
	public static String getProperty(String filePath, String name) throws IOException  {
		if (StringUtils.isBlank(filePath) || StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("The parameters must not be null");
		}

		if (!filePath.startsWith("/")) {
		    filePath = "/"+filePath;
        }
		Properties prop = PROPS.get(filePath);
		if (prop == null) {
			prop = new Properties();
			prop.load(PropertiesUtil.class.getResourceAsStream(filePath));
			PROPS.put(filePath, prop);
		}
		
		return prop.getProperty(name);
	}

	/**
	 * 
	 * @Description 根据name获取properties文件中的value, 如果为空返回默认值
	 * @author youps-a
	 * @date 2015年11月26日 上午9:42:22
	 * @param filePath     properties文件路径(classpath中的相对路径)
	 * @param name         所要获取的properties文件中的属性名
	 * @param defaultValue 如果没有对应的属性值要返回的默认值
	 * @return
	 * @throws IOException 
	 */
	public static String getProperty(String filePath, String name, String defaultValue) throws IOException{

	    String value = getProperty(filePath, name);
		
		return StringUtils.isBlank(value) ? defaultValue : value;
	}
	
	/**
     * @功能: properties文件中的name,value (已过期, 会打乱properties文件中顺序, 其中加锁是为了防止多个线程同时写文件)
     * @作者: yangc
     * @创建日期: 2013-11-21 下午07:02:38
     * @param filePath properties文件路径(classpath中的相对路径)
     * @param name
     * @param value
     * 
     * 只会改变编译后 classes 中的文件，而不会修改项目原文件，使用时需注意
	 * @throws IOException 
     */
    @Deprecated
    public static synchronized void setProperty(String filePath, String name, String value) throws IOException {
        if (StringUtils.isBlank(filePath) || StringUtils.isBlank(name) || StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("The parameters must not be null");
        }
        if (!filePath.startsWith("/")) {
            filePath = "/"+filePath;
        }
        BufferedWriter bw = null;
        FileOutputStream fos = null;
        OutputStreamWriter os = null;
        try {
            Properties prop = PROPS.get(filePath);
            if (prop == null) {
                prop = new Properties();
                prop.load(PropertiesUtil.class.getResourceAsStream(filePath));
                PROPS.put(filePath, prop);
            }
            prop.put(name, value);
            String path = PropertiesUtil.class.getResource(filePath).getFile();
            // jdk bug 会将 空格 转换为 %20 ,因此过滤
            path = path.replace("%20", " ");
            fos = new FileOutputStream(path);
            os = new OutputStreamWriter(fos, UTF_8);
            bw = new BufferedWriter(os);
            prop.store(bw, "保存properties配置文件");
        } finally {
            IOUtils.closeQuietly(bw);
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(fos);
        }
    }

	public static void main(String[] args) {
		try {
		    System.out.println(PropertiesUtil.getProperty("/property/i n it.properties", "jdbc.dbcp.dataSource.maxIdle"));
            PropertiesUtil.setProperty("/property/i n it.properties", "jdbc.dbcp.dataSource.maxIdle", "60");
            System.out.println(PropertiesUtil.getProperty("/property/i n it.properties", "jdbc.dbcp.dataSource.maxIdle"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
