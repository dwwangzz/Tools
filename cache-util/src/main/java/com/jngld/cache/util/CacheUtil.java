package com.jngld.cache.util;

import com.jngld.cache.util.ehcache.EHCache;
import com.jngld.cache.util.ehcache.IEHCache;
import com.jngld.cache.util.memcached.IMemcachedUtil;
import com.jngld.cache.util.memcached.MemcachedUtil;

/**
 * ehcache工具类
 * @author wangzz-a
 * @version $Id: EHCacheUtil.java, v 0.1 2015年12月9日 上午9:09:24 wangzz-a Exp $
 */
public class CacheUtil {
	
	/**EHCache工具类*/
	public static final IEHCache ehcache = EHCache.ehCache;
	
	/**memcached工具类*/
	public static final IMemcachedUtil memcached = MemcachedUtil.memcachedUtil;
	
	
}
