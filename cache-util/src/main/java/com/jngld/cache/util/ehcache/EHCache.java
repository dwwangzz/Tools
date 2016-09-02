package com.jngld.cache.util.ehcache;

import com.jngld.cache.util.entity.EHCacheConfig;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import java.util.List;

/**
 * EHCache工具类
 * @author wangzz-a
 * @version $Id: EHCacheUtil.java, v 0.1 2015年12月2日 下午2:48:11 wangzz-a Exp $
 */
public class EHCache implements IEHCache{
	
	public  static EHCache ehCache = null;
	private static CacheManager cacheManager = null;
	private static Cache cache = null;
	
	//静态代码块 用来初始化cacheManager
	static {
		ehCache = new EHCache();
		ehCache.initCacheManager();
		ehCache.getCache("cache");
	}
	
	//私有构造函数
	private EHCache(){}

	/**
	 * 初始化缓存管理容器
	 * @author wangzz-a
	 * @return CacheManager
	 * @date 2015年12月8日 上午9:16:16
	 */
	public CacheManager initCacheManager() {
		try {
			if (cacheManager == null)
				cacheManager = CacheManager.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheManager;
	}

	/**
	 * 初始化缓存管理容器
	 * @author wangzz-a
	 * @param path ehcache.xml存放的路徑
	 * @return CacheManager
	 * @date 2015年12月8日 上午9:16:39
	 */
	@SuppressWarnings("static-access")
	public CacheManager initCacheManager(String path) {
		
		try {
			if (cacheManager == null) {
				cacheManager = CacheManager.getInstance().create(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheManager;
	}

	/**
	 * 初始化cache
	 * @author wangzz-a
	 * @param cacheName 缓存名称
	 * @param timeToLiveSeconds 有效时间
	 * @return Cache
	 * @date 2015年12月8日 上午9:29:37
	 */
	public Cache initCache(String cacheName, long timeToLiveSeconds) {
		return initCache(cacheName, EHCacheConfig.MAXELEMENTSINMEMORY, EHCacheConfig.OVERFLOWTODISK,
				EHCacheConfig.ETERNAL, timeToLiveSeconds, EHCacheConfig.TIMETOIDLESECONDS);
	}

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
	@SuppressWarnings("deprecation")
	public Cache initCache(String cacheName, int maxElementsInMemory, boolean overflowToDisk, boolean eternal,
		long timeToLiveSeconds, long timeToIdleSeconds) {
		CacheManager singletonManager = CacheManager.create();
		Cache myCache = singletonManager.getCache(cacheName);
		if (myCache != null) {
			CacheConfiguration config = myCache.getCacheConfiguration();
			config.setTimeToLiveSeconds(timeToLiveSeconds);
			config.setMaxEntriesLocalHeap(maxElementsInMemory);
			config.setOverflowToDisk(overflowToDisk);
			config.setEternal(eternal);
			config.setTimeToIdleSeconds(timeToIdleSeconds);
		}
		if (myCache == null) {
			Cache memoryOnlyCache = new Cache(cacheName, maxElementsInMemory, overflowToDisk, eternal,
					timeToLiveSeconds, timeToIdleSeconds);
			singletonManager.addCache(memoryOnlyCache);
			myCache = singletonManager.getCache(cacheName);
		}
		return myCache;
	}

	/**
	 * 获得所有的cache名称
	 * @author wangzz-a
	 * @return String[]
	 * @date 2015年12月8日 下午2:18:41
	 */
	public String[] getCacheNames(){
		checkCacheManager();
		return cacheManager.getCacheNames();
	}
	
	/**
	 * 初始化cache（缓存容器）
	 * @author wangzz-a
	 * @param cacheName 缓存名称
	 * @return Cache
	 * @date 2015年12月8日 上午9:21:12
	 */
	public Cache getCache(String cacheName) {
		checkCacheManager();
		if (null == cacheManager.getCache(cacheName)) {
			cacheManager.addCache(cacheName);
		}
		cache = cacheManager.getCache(cacheName);
		return cache;
	}

	/**
	 * 修改cache（缓存容器）配置
	 * @param cacheName 缓存名
	 * @param timeToLiveSeconds 有效时间
	 * @param maxElementsInMemory 最大数量
	 * @date 2015年12月8日 上午9:30:48
	 */
	public boolean modifyCache(String cacheName, long timeToLiveSeconds, long maxElementsInMemory) {
		if ((cacheName != null && cacheName.length() > 0) && timeToLiveSeconds != 0L && maxElementsInMemory != 0) {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			CacheConfiguration config = myCache.getCacheConfiguration();
			config.setTimeToLiveSeconds(timeToLiveSeconds);
			config.setMaxEntriesLocalHeap(maxElementsInMemory);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 向默认缓存容器cache中添加缓存
	 * @author wangzz-a
	 * @param key 关键字
	 * @param value 值
	 * @date 2015年12月8日 上午9:31:08
	 */
	public void set(String key, Object value) {
		checkCache();
		// 创建Element,然后放入Cache对象中
		Element element = new Element(key, value);
		cache.put(element);
	}

	/**
	 * 向指定容器中设置值
	 * @author wangzz-a
	 * @param cacheName 容器名
	 * @param key 键
	 * @param value 值
	 * @date 2015年12月8日 上午9:32:13
	 */
	public void setValue(String cacheName, String key, Object value) {
		checkCacheManager();
		Cache myCache = cacheManager.getCache(cacheName);
		if (myCache == null) {
			myCache = getCache(cacheName);
		}
		myCache.put(new Element(key, value));
	}

	/**
	 * 向指定容器中设置值并指定此对象的存货时间（单位s）
	 * @author wangzz-a
	 * @param cacheName 容器名
	 * @param key 键
	 * @param value 值
	 * @param timeToLiveSeconds 存活时间
	 * @date 2015年12月8日 上午9:32:24
	 */
	@SuppressWarnings("deprecation")
	public void setValue(String cacheName, String key, Object value, Integer timeToLiveSeconds) {
		CacheManager myManager = CacheManager.create();
		Cache myCache = myManager.getCache(cacheName);
		if (myCache == null) {
			initCache(cacheName, timeToLiveSeconds);
			myCache = myManager.getCache(cacheName);
		}
		myCache.put(new Element(key, value, EHCacheConfig.ETERNAL, EHCacheConfig.TIMETOIDLESECONDS,timeToLiveSeconds));
	}
	
	/**
	 * 获取默认cache中的值
	 * @author wangzz-a
	 * @param key 关键字
	 * @return Object
	 * @date 2015年12月8日 上午9:31:34
	 */
	public Object get(String key) {
		checkCache();
		Element element = cache.get(key);
		if (null == element) {
			return null;
		}
		return element.getObjectValue();
	}
	
	/**
	 * 从指定容器中取值
	 * @author wangzz-a
	 * @param cacheName 容器名称
	 * @param key 关键字
	 * @return Object
	 * @date 2015年12月8日 下午4:30:22
	 */
	public Object getValue(String cacheName, String key) {
		CacheManager myManager = CacheManager.create();
		Cache myCache = myManager.getCache(cacheName);
		if (myCache == null) {
			myCache = getCache(cacheName);
		}
		return myCache.get(key).getObjectValue();
	}
	
	/**
	 * 获取默认Cache所有的Keys
	 * @return List 所有cache的key
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	@SuppressWarnings("rawtypes")
	public List getKeys() {
		checkCache();
		return cache.getKeys();
	}
	
	/**
	 * 获取指定Cache所有的Keys
	 * @return List 所有cache的key
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	@SuppressWarnings("rawtypes")
	public List getKeys(String cacheName) {
		if (null == cacheManager) {
			throw new IllegalArgumentException("调用前请先初始化CacheManager值：EHCacheUtil.initCacheManager");
		}
		Cache cache = cacheManager.getCache(cacheName);
		if (null == cache) {
			throw new IllegalArgumentException("your cacheName is null");
		}
		return cache.getKeys();
	}

	/**
	 * 释放CacheManage
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	public void shutdown() {
		cacheManager.shutdown();
	}
	
	/**
	 * 删除默认的cache容器
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:32:42
	 */
	public void removeCache() {
		checkCacheManager();
		cacheManager.removeCache("cache");
	}
	
	/**
	 * 删除指定的cache容器
	 * @author wangzz-a
	 * @param cacheName
	 * @date 2015年12月8日 上午9:32:42
	 */
	public void removeCache(String cacheName) {
		checkCacheManager();
		cacheManager.removeCache(cacheName);
	}
	
	
	/**
	 * 移除所有cache
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	public void removeAllCache() {
		checkCacheManager();
		cacheManager.removeAllCaches();
	}
	
	/**
	 * 移除默认cache容器中的元素
	 * @author wangzz-a
	 * @param key 关键字
	 * @date 2015年12月8日 上午9:33:58
	 */
	public void remove(String key) {
		checkCache();
		cache.remove(key);
	}

	/**
	 * 删除指定cache容器中的指定元素
	 * @author wangzz-a
	 * @param cacheName 容器名
	 * @param key 关键字
	 * @date 2015年12月8日 上午9:33:31
	 */
	public void removeElment(String cacheName, String key) {
		checkCacheManager();
		Cache myCache = cacheManager.getCache(cacheName);
		myCache.remove(key);
	}
	
	/**
	 * 删除指定容器中的所有元素
	 * @author wangzz-a
	 * @param cacheName 容器名
	 * @date 2015年12月8日 上午9:33:46
	 */
	public void removeAllElment(String cacheName) {
		checkCacheManager();
		Cache myCache = cacheManager.getCache(cacheName);
		myCache.removeAll();
	}

	/**
	 * 移除默认cache所有Element
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	public void removeAllElment() {
		checkCache();
		cache.removeAll();
	}

	/**
	 * 检测cacheManager
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	private void checkCacheManager() {
		if (null == cacheManager) {
			throw new IllegalArgumentException("调用前请先初始化CacheManager值：EHCacheUtil.initCacheManager");
		}
	}

	/**
	 * 检测默认cache
	 * @author wangzz-a
	 * @date 2015年12月8日 上午9:33:58
	 */
	private void checkCache() {
		if (null == cache) {
			throw new IllegalArgumentException("调用前请先初始化Cache值：EHCacheUtil.initCache(参数)");
		}
	}

}
