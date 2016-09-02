package com.jngld.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Cookie工具类
 * @author (作者) huangqw
 * @date (开发日期) 2015年11月27日 上午10:57:37 
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class CookieUtil {
	
	/**
	 * 
	 * 设置cookie（关闭浏览器cookie消失）
	 * @author huangqw
	 * @param response
	 * @param name
	 * @param valuevoid
	 * @date 2015年11月27日下午4:00:13
	 */
	public static void addCookie(HttpServletResponse response, String name, String value) { 
		addCookie(response, name, value, -1);
	} 
	
	/**
	 * 
	 * 设置cookie（设置cookie存在的时间）
	 * @author huangqw
	 * @param response
	 * @param name cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 * @date 2015年11月27日上午11:03:38
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) { 
		addCookie(response, name, value, "/", maxAge);
	} 
	
	/**
	 * 设置cookie（设置cookie存在的时间、站点）
	 * @author liuy-8
	 * @date 2015年12月3日 下午2:28:28 
	 * @param response
	 * @param name cookie名字
	 * @param value cookie值
	 * @param path 应用路径
	 * @param maxAge cookie生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, String path, int maxAge) { 
	    Cookie cookie = new Cookie(name,value); 
	    cookie.setPath(path);
	    cookie.setMaxAge(maxAge); 
	    response.addCookie(cookie); 
	} 
	
	/**
	 * 
	 * 根据名字获取cookie 
	 * @author huangqw
	 * @param request
	 * @param name cookie的名字
	 * @return 对应的cookie，如果不存在则返回null
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Cookie result = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(name)) {
					result = cookie;
					break;
				}
			}
		}
		return result;
	} 

	/**
	 * 清空cookie
	 * @author huangqw
	 * @param request
	 * @param response
	 * @date 2015年11月27日下午2:51:08
	 */
	public static void clearCookie(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		// cookies不为空，则清除
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				deleteCookie(response, cookie.getName());
			}
		}
	}
	
	/**
	 * 删除特定cookie
	 * @author huangqw
	 * @param request
	 * @param response
	 * @param cookieName 需要清空的cookie的名称
	 * @date 2015年11月27日下午2:53:49
	 * @throws
	 */
	public static void deleteCookie(HttpServletResponse response, String cookieName) {
		addCookie(response, cookieName, "", 0);
	}
	
}
