package com.jngld.utils.kryo;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.esotericsoftware.kryo.Kryo;

/**
 * kryo对象池工具类
 * @author wangzz-a
 * @version $Id: KryoPoolUtil.java, v 0.1 2015年12月28日 下午3:35:35 wangzz-a Exp $
 */
public class KryoPoolUtil {
	
	//对象池
	public static GenericObjectPool<Kryo> pool = null;
	
	//用于初始化对象池
	static {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig(); 
        config.setMaxTotal(50); //整个池最大值  
        config.setBlockWhenExhausted(true); 
        config.setMaxWaitMillis(-1); //获取不到永远等待  
        config.setNumTestsPerEvictionRun(Integer.MAX_VALUE); // always test all idle objects
        config.setTestOnBorrow(true);
        config.setTestOnReturn(false);
        config.setTestWhileIdle(false);
        config.setTimeBetweenEvictionRunsMillis(1 * 60000L); //-1不启动。默认1min一次  
        config.setMinEvictableIdleTimeMillis(10 * 60000L); //可发呆的时间,10mins
        config.setTestWhileIdle(false);  //发呆过长移除的时候是否test一下先 
        
        KryoPoolableFactory factory = new KryoPoolableFactory();
		pool = new GenericObjectPool<Kryo>(factory, config);
		System.out.println("KryoPoolUtil init success !");
	}
	
	/**
	 * 借用Kryo对象
	 * @author wangzz-a
	 * @return Kryo
	 * @throws Exception 
	 * @date 2015年12月28日 下午3:34:17
	 */
	public static Kryo getKryo() throws Exception{
		Kryo kryo = null;
		kryo = pool.borrowObject();
		
		return kryo;
	}
	
	/**
	 * 归还Kryo对象
	 * @author wangzz-a
	 * @param kryo
	 * @return
	 * @date 2015年12月28日 下午3:34:04
	 */
	public static boolean returnKryo(Kryo kryo){
		boolean result = false;
		if(kryo!=null){
			pool.returnObject(kryo);
			result = true;
		}
		return result;
	}
	
}
