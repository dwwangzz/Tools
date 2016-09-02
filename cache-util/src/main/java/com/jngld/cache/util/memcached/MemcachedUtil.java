/**
 * glodon.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 * com.glodon.gcxx.util
 */
package com.jngld.cache.util.memcached;

import java.util.Date;
import com.whalin.MemCached.MemCachedClient;

/**
 * @Description memcached工具类，封装memcached插入、更新、删除等操作
 * @author (作者) liuy-8
 * @date (开发日期) 2015年5月29日 上午10:24:10
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class MemcachedUtil implements IMemcachedUtil{
	
	public static MemcachedUtil memcachedUtil = null;
	
	private static MemCachedClient cachedClient = null;
	
	static {
		memcachedUtil = new MemcachedUtil();
		if (cachedClient == null)
			cachedClient = new MemCachedClient();
	}
	
	//私有化构造函数
	private MemcachedUtil(){}

	/**
	 * @Description 添加新的键值对，默认最少使用算法淘汰。如果键已经存在，则之前的值将被替换。
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:09:17 
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	public boolean set(String key, Object value) {
		return setExp(key, value, null);
	}

	/**
	 * @Description 添加新的键值对。如果键已经存在，则之前的值将被替换。
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:10:08 
	 * @param key 键
	 * @param value 值
	 * @param time 过期时间,单位ms
	 * @return 是否成功
	 */
	public boolean set(String key, Object value, int time) {
		return setExp(key, value, new Date(time));
	}

	/**
	 * @Description 添加新的键值对。如果键已经存在，则之前的值将被替换。
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:13:18 
	 * @param key 键
	 * @param value 值
	 * @param expire 例：New Date(1000*10)：十秒后过期
	 * @return 是否成功
	 */
	private boolean setExp(String key, Object value, Date expire) {
		boolean flag = false;
		flag = cachedClient.set(key, value, expire);
		return flag;
	}

	/**
	 * @Description 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对，默认最少使用算法淘汰
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:13:50 
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	public boolean add(String key, Object value) {
		return addExp(key, value, null);
	}

	/**
	 * @Description 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:27:40 
	 * @param key 键
	 * @param value 值
	 * @param time 过期时间,单位毫秒
	 * @return 是否成功
	 */
	public boolean add(String key, Object value, int time) {
		return addExp(key, value, new Date(time));
	}

	/**
	 * @Description 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:30:29 
	 * @param key 键
	 * @param value 值
	 * @param expire 例：New Date(1000*10)：十秒后过期
	 * @return 是否成功
	 */
	private boolean addExp(String key, Object value, Date expire) {
		boolean flag = false;
		flag = cachedClient.add(key, value, expire);
		return flag;
	}

	/**
	 * @Description 仅当键已经存在时，replace 命令才会替换缓存中的键，默认最少使用算法淘汰
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:42:22 
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	public boolean replace(String key, Object value) {
		return replaceExp(key, value, null);
	}

	/**
	 * @Description 仅当键已经存在时，replace 命令才会替换缓存中的键。
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:07:51 
	 * @param key 键
	 * @param value 值
	 * @param time 过期时间,单位毫秒
	 * @return 是否成功
	 */
	public boolean replace(String key, Object value, int time) {
		return replaceExp(key, value, new Date(time));
	}

	/**
	 * @Description 仅当键已经存在时，replace 命令才会替换缓存中的键。
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:09:22 
	 * @param key 键
	 * @param value 值
	 * @param expire 过期时间 例：New Date(1000*10)：十秒后过期
	 * @return 是否成功
	 */
	private boolean replaceExp(String key, Object value, Date expire) {
		boolean flag = false;
		flag = cachedClient.replace(key, value, expire);
		return flag;
	}

	/**
	 * @Description 命令用于检索与之前添加的键值对相关的值
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:09:45 
	 * @param key 键
	 * @return 得到与键对应的值
	 */
	public Object get(String key) {
		Object obj = null;
		obj = cachedClient.get(key);
		return obj;
	}

	/**
	 * @Description 删除 memcached 中的任何现有值。
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:10:57 
	 * @param key 键
	 * @return 是否成功
	 */
	public boolean delete(String key) {
		return deleteExp(key, null);
	}

	/**
	 * @Description 删除 memcached 中的任何现有值。
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:33:39 
	 * @param key 键
	 * @param time 阻塞时间，单位ms，禁止使用同样的键保存新数据，set方法除外
	 * @return 是否成功
	 */
	public boolean delete(String key, int time) {
		return deleteExp(key, new Date(time));
	}
	
	/**
	 * @Description 禁止使用同样的键保存新数据，set方法除外
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:37:52 
	 * @param key 键
	 * @param expire 阻塞时间，单位ms，禁止使用同样的键保存新数据，set方法除外
	 * @return 是否成功
	 */
	@SuppressWarnings("deprecation")
	private boolean deleteExp(String key, Date expire) {
		boolean flag = false;
		flag = cachedClient.delete(key, expire);
		return flag;
	}

	/**
	 * @Description 清理缓存中的所有键/值对
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:38:26 
	 * @return 是否成功
	 */
	public boolean flushAll() {
		boolean flag = false;
		flag = cachedClient.flushAll();
		return flag;
	}

}
