package com.jngld.cache.util.memcached;

public interface IMemcachedUtil {
	
	/**
	 * @Description 添加新的键值对，默认最少使用算法淘汰。如果键已经存在，则之前的值将被替换。
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:09:17 
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	public boolean set(String key, Object value);

	/**
	 * @Description 添加新的键值对。如果键已经存在，则之前的值将被替换。
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:10:08 
	 * @param key 键
	 * @param value 值
	 * @param time 过期时间,单位ms
	 * @return 是否成功
	 */
	public boolean set(String key, Object value, int time);

	/**
	 * @Description 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对，默认最少使用算法淘汰
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:13:50 
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	public boolean add(String key, Object value);

	/**
	 * @Description 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:27:40 
	 * @param key 键
	 * @param value 值
	 * @param time 过期时间,单位毫秒
	 * @return 是否成功
	 */
	public boolean add(String key, Object value, int time);

	/**
	 * @Description 仅当键已经存在时，replace 命令才会替换缓存中的键，默认最少使用算法淘汰
	 * @author liuy-8
	 * @date 2015年5月29日 下午2:42:22 
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	public boolean replace(String key, Object value);

	/**
	 * @Description 仅当键已经存在时，replace 命令才会替换缓存中的键。
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:07:51 
	 * @param key 键
	 * @param value 值
	 * @param time 过期时间,单位毫秒
	 * @return 是否成功
	 */
	public boolean replace(String key, Object value, int time);

	/**
	 * @Description 命令用于检索与之前添加的键值对相关的值
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:09:45 
	 * @param key 键
	 * @return 得到与键对应的值
	 */
	public Object get(String key);

	/**
	 * @Description 删除 memcached 中的任何现有值。
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:10:57 
	 * @param key 键
	 * @return 是否成功
	 */
	public boolean delete(String key);

	/**
	 * @Description 删除 memcached 中的任何现有值。
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:33:39 
	 * @param key 键
	 * @param time 阻塞时间，单位ms，禁止使用同样的键保存新数据，set方法除外
	 * @return 是否成功
	 */
	public boolean delete(String key, int time);


	/**
	 * @Description 清理缓存中的所有键/值对
	 * @author liuy-8
	 * @date 2015年5月29日 下午3:38:26 
	 * @return 是否成功
	 */
	public boolean flushAll();
}
