package com.jngld.springutil;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * @Description 切面，用于拦截异常
 * @author (作者) liuy-8
 * @date (开发日期) 2015年8月11日 下午3:57:47
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
@Aspect
@Component
public class ErrorInterceptor {
	
    /**
     * 
     * @Description 切入点，所有 @ RequestMapping注解
     * @author youps-a
     * @date 2015年12月3日 上午10:39:35
     */
	@Pointcut(value="execution(* *(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logAnnotatedMethods() {
    }
    
    /**
     * @Description 拦截异常，写入日志
     * @author liuy-8
     * @date 2015年8月11日 下午3:57:20 
     * @param point 连接点
     * @param throwing 被拦截方法产生的异常信息
     * @throws IOException PrintWriter关闭异常
     */
    @AfterThrowing(pointcut="logAnnotatedMethods()", throwing="throwing")
	public void afterThrowsAdvice(JoinPoint point, RuntimeException throwing) {
    	//日志
    	Logger LOGGER = Logger.getLogger(point.getTarget().getClass());
    	LOGGER.error(throwing.getMessage(), throwing);
	}

}
