package com.jngld.cache.util.entity;

import java.io.Serializable;

/**
 * ehcache默认配置类
 * @author wangzz-a
 * @version $Id: EHCacheConfig.java, v 0.1 2015年12月8日 下午3:24:49 wangzz-a Exp $
 */
public class EHCacheConfig implements Serializable{
 
	private static final long serialVersionUID = 1L;

	/** 
     * 元素最大数量 
     */  
    public static int MAXELEMENTSINMEMORY = 50000; 
    
    /** 
     * 是否把溢出数据持久化到硬盘 
     */  
    public static boolean OVERFLOWTODISK = true;  
  
    /** 
     * 是否会死亡 
     */  
    public static boolean ETERNAL = true;  
  
    /** 
     * 缓存的间歇时间 
     */  
    public static int TIMETOIDLESECONDS = 600;  
  
    /** 
     * 存活时间(默认一天)
     */  
    public static int TIMETOlIVESECONDS = 60 * 60 * 24;  
  
    /** 
     * 需要持久化到硬盘否 
     */  
    public static boolean DISKPERSISTENT = false;  
  
    /** 
     * 内存存取策略 
     */  
    public static String MEMORYSTOREEVICTIONPOLICY = "LFU";  
    
}
