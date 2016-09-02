package com.jngld.cache.util.ehcache;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public interface IEHCache {
	
	/**
	 * 初始化缓存管理容器
	 * @author wangzz-a
	 * @return CacheManager
	 * @date 2015年12月8日 上午9:16:16
	 */
	public CacheManager initCacheManager();

	/**
	 * 初始化缓存管理容器
	 * @author wangzz-a
	 * @param path ehcache.xml存放的路徑
	 * @return CacheManager
	 * @date 2015年12月8日 上午9:16:39
	 */
	public CacheManager initCacheManager(String path);

	/**
	 * 初始化cache
	 * @author wangzz-a
	 * @param cacheName 缓存名称
	 * @param timeToLiveSeconds 有效时间
	 * @return Cache
	 * @date 2015年12月8日 上午9:29:37
	 */
	public Cache initCache(String cacheName, long timeToLiveSeconds);

	/**
	 * 初始化缓存
	 * @param cacheName 缓存名称
	 * @param maxElementsInMemory 元素最大数量
	 * @param overflowToDisk 是否持久化到硬盘
	 * @param eternal 是否会死亡
	 * @param timeToLiveSeconds 缓存存活时间
	 * @param timeToIdleSeconds 缓存的间隔时间
	 * @return Cache
	 * @date 2015年12月8日 上午9:30:31
	 */
	public Cache initCache(String cacheName, int maxElementsInMemory, boolean overflowToDisk, boolean eternal,
		long timeToLiveSeconds, long timeToIdleSeconds);

	/**
	 * 获得所有的cache名称
	 * @author wangzz-a
	 * @return String[]
	 * @date 2015年12月8日 下午2:18:41
	 */
	public String[] getCacheNames();
	
	/**
	 * 初始化cache（缓存容器）
	 * @author wangzz-a
	 * @param cacheName 缓存名称
	 * @return Cache
	 * @date 2015年12月8日 上午9:21:12
	 */
	public Cache getCache(String cacheName);

	/**
	 * 修改cache（缓存容器）配置
	 * @param cacheName 缓存名
	 * @param timeToLiveSeconds 有效时间
	 * @param maxElementsInMemory 最大数量
	 * @date 2015年12月8日 上午9:30:48
	 */
	public boolean modifyCache(String cacheName, long timeToLiveSeconds, long maxElementsInMemory);
	
	/**
	 * 向默认缓存容器cache中添加缓存
	 * @author wangzz-a
	 * @param key 关键字
	 * @param value 值
	 * @date 2015年12月8日 上午9:31:08
	 */
	public void set(String key, Object value);

	/**
	 * 向指定容器中设置值
	 * @author wangzz-a
	 * @param cacheName 容器名
	 * @param key 键
	 * @param value 值
	 * @date 2015年12月8日 上午9:32:13
	 */
	public void setValue(String cacheName, String key, Object value);

	/**
	 * 向指定容器中设置值并指定此对象的存货时间（单位s）
	 * @author wangzz-a
	 * @param cacheName 容器名
	 * @param key 键
	 * @param value 值
	 * @param timeToLiveSeconds 存活时间
	 * @date 2015年12月8日 上午9:32:24
	 */
	public void setValue(String cacheName, String key, Object value, Integer timeToLiveSeconds);
	
	/**
	 * 获取默认cache中的值
	 * @author wangzz-a
	 * @param key 关键字
	 * @return Object
	 * @date 2015年12月8日 上午9:31:34
	 */
	public Object get(String key);
	
	/**
	 * 从指定容器中取值
	 * @author wangzz-a
	 * @param cacheName 容器名称
	 * @param key 关键字
	 * @return Object
	 * @date 2015年12月8日 下午4:30:22
	 */
	public Object getValue(String cacheName, String key);
	
	/**
	 * 获取默认Cache所有的Keys
	 * @return List 所有cache的key
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	@SuppressWarnings("rawtypes")
	public List getKeys();
	
	/**
	 * 获取指定Cache所有的Keys
	 * @return List 所有cache的key
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	@SuppressWarnings("rawtypes")
	public List getKeys(String cacheName);

	/**
	 * 释放CacheManage
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	public void shutdown();
	
	/**
	 * 删除默认的cache容器
	 * @author wangzz-a
	 * @param vesselName
	 * @date 2015年12月8日 上午9:32:42
	 */
	public void removeCache();
	
	/**
	 * 删除指定的cache容器
	 * @author wangzz-a
	 * @param cacheName
	 * @date 2015年12月8日 上午9:32:42
	 */
	public void removeCache(String cacheName);
	
	
	/**
	 * 移除所有cache
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	public void removeAllCache();
	
	/**
	 * 移除默认cache容器中的元素
	 * @author wangzz-a
	 * @param key 关键字
	 * @date 2015年12月8日 上午9:33:58
	 */
	public void remove(String key);

	/**
	 * 删除指定cache容器中的指定元素
	 * @author wangzz-a
	 * @param cacheName 容器名
	 * @param key 关键字
	 * @date 2015年12月8日 上午9:33:31
	 */
	public void removeElment(String cacheName, String key);
	
	/**
	 * 删除指定容器中的所有元素
	 * @author wangzz-a
	 * @param cacheName 容器名
	 * @date 2015年12月8日 上午9:33:46
	 */
	public void removeAllElment(String cacheName);

	/**
	 * 移除默认cache所有Element
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	public void removeAllElment();
}
