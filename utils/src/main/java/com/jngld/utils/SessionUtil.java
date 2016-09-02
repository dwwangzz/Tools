package com.jngld.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
  /**
   * 
   * 获得项目路径
   * @author xus-a
   * @date 2015年11月27日 下午2:14:17 
   * @param request
   * @return
   */
  public static String getRealPath(HttpServletRequest request) {
    return getSession(request).getServletContext().getRealPath("/");
  }

  /**
   * 
   * 获取tomcat安装路径
   * @author xus-a
   * @date 2015年11月27日 下午2:14:21 
   * @return
   */
  public static String getTomcatPath() {
    return System.getProperty("catalina.home").replaceAll("\\\\", "/") + "/";
  }
  
  /**
   * 
   * 得到session
   * @author xus-a
   * @date 2015年11月27日 下午2:14:30 
   * @param request
   * @return
   */
  public static HttpSession getSession(HttpServletRequest request) {
    HttpSession session = request.getSession();
    return session;
  }
  /**
   * 
   * 将value对象以name名称绑定到会话
   * @author xus-a
   * @date 2015年11月27日 下午2:19:06 
   * @param request
   * @param name  名称 
   * @param Value 对象
   */
  public static void setSessionAttribute(HttpServletRequest request,String name,Object Value){
    getSession(request).setAttribute(name, Value);
  }
  /**
   * 
   * 注销session
   * @author xus-a
   * @date 2015年11月27日 下午2:24:55 
   * @param request
   */
  public static void invalidate(HttpServletRequest request){
    getSession(request).invalidate();
  }
  /**
   * 
   * 获得session的编号
   * @author xus-a
   * @date 2015年11月27日 下午2:33:28 
   * @param request
   * @return
   */
  public static String getSessionId(HttpServletRequest request){
    return getSession(request).getId();
  }
  
  /**
   * 
   * 取得name的属性值，如果属性不存在则返回null
   * @author xus-a
   * @date 2015年11月27日 下午2:38:31 
   * @param request
   * @param name
   * @return
   */
  public static Object getSessionAttribute(HttpServletRequest request,String name){
    return getSession(request).getAttribute(name);
  }
}
