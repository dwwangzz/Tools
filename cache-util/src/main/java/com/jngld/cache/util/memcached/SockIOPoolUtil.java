package com.jngld.cache.util.memcached;

import com.whalin.MemCached.SockIOPool;

/**
 * 封装memcached的SockIOPool类<br>
 * 用于初始化
 * @author wangzz-a
 * @version $Id: SockIOPoolUtil.java, v 0.1 2015年12月11日 下午3:08:55 wangzz-a Exp $
 */
public class SockIOPoolUtil {
	
	// 构造函数
	public SockIOPoolUtil() {
	}
	public SockIOPoolUtil(String[] servers) {
		setServers(servers);
	}
	public SockIOPoolUtil(String[] servers, int initConn, int minConn, int maxConn, long maintSleep, boolean nagle, int socketTO) {
		setServers(servers);
		setInitConn(initConn);
		setMinConn(minConn);
		setMaxConn(maxConn);
		setMaintSleep(maintSleep);
		setNagle(nagle);
		setSocketTO(socketTO);
	}
	
	//获得SockIOPool的实例，这里只用了默认的初始化方法
	private static SockIOPool localSockIOPool = SockIOPool.getInstance();
	
	//初始化SockIOPool
	public void initialize() {
		localSockIOPool.initialize();
	}

	public boolean isInitialized() {
		return localSockIOPool.isInitialized();
	}

	public void shutDown() {
		localSockIOPool.shutDown();
	}
	
	public String[] getServers() {
		return localSockIOPool.getServers();
	}

	public void setServers(String[] servers) {
		localSockIOPool.setServers(servers);
	}

	public int getInitConn() {
		return localSockIOPool.getInitConn();
	}

	public void setInitConn(int initConn) {
		localSockIOPool.setInitConn(initConn);
	}

	public int getMinConn() {
		return localSockIOPool.getMinConn();
	}

	public void setMinConn(int minConn) {
		localSockIOPool.setMinConn(minConn);
	}

	public int getMaxConn() {
		return localSockIOPool.getMaxConn();
	}

	public void setMaxConn(int maxConn) {
		localSockIOPool.setMaxConn(maxConn);
	}

	public void setMaintSleep(long maintSleep) {
		localSockIOPool.setMaintSleep(maintSleep);
	}

	public long getMaintSleep() {
		return localSockIOPool.getMaintSleep();
	}

	public boolean getNagle() {
		return localSockIOPool.getNagle();
	}

	public void setNagle(boolean nagle) {
		localSockIOPool.setNagle(nagle);
	}

	public int getSocketTO() {
		return localSockIOPool.getSocketTO();
	}

	public void setSocketTO(int socketTO) {
		localSockIOPool.setSocketTO(socketTO);
	}
	
	/*public static void main(String[] args) {
	SockIOPool pool = SockIOPool.getInstance();
	pool.setServers(new String[] { "172.12.121.1" });
	MemCachedClient client = new MemCachedClient();
	System.out.println(client);
	}*/
	
}