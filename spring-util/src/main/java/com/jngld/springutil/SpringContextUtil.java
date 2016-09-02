package com.jngld.springutil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * 
 * @Description spring上下文工具类
 * @author (作者) huangqw
 * @date (开发日期) 2015年11月26日 上午10:45:33 
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
@Component
public class SpringContextUtil implements ApplicationContextAware{

	// Spring应用上下文环境
	private static ApplicationContext applicationContext;
		
	/**
	 * 
	 * @Decripttion 实现ApplicationContextAware接口的回调方法，设置上下文环境 
	 * @author huangqw
	 * @param applicationContext 上下文对象
	 * @date 2015年11月26日上午10:59:51
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 
	 * @Decripttion 返回上下文对象
	 * @author huangqw
	 * @returnApplicationContext
	 * @date 2015年11月26日上午11:00:51
	 */
	public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
	/**
	 * 
	 * @Decripttion 获取对象
	 * @author huangqw
	 * @param name bean注册名 
	 * @return 一个以所给名字注册的bean的实例 
	 * @throws BeansException
	 * @date 2015年11月26日上午11:02:15
	 */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
 
    /**
     * 
     * @Decripttion 获取类型为requiredType的对象，如果bean不能被类型转换，相应的异常将会被抛出
     * @author huangqw
     * @param name bean注册名 
     * @param requiredType 返回对象类型
     * @return 返回requiredType类型对象
     * @throws BeansException
     * @date 2015年11月26日上午11:03:13
     * @throws
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getBean(String name, Class requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }
 
    /**
     * 
     * @Decripttion 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     * @author huangqw
     * @param name bean注册名
     * @returnboolean
     * @date 2015年11月26日上午11:05:14
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }
 
    /**
     * 
     * @Decripttion 判断以给定名字注册的bean定义是一个singleton还是一个prototype。  
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常
     * @author huangqw
     * @param name bean注册名
     * @return
     * @throws NoSuchBeanDefinitionException
     * @date 2015年11月26日上午11:06:50
     * @throws
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }
 
    /**
     * 
     * @Decripttion 返回注册对象的类型 
     * @author huangqw
     * @param name bean注册名
     * @return Class 注册对象的类型 
     * @throws NoSuchBeanDefinitionException
     * @date 2015年11月26日上午11:08:02
     * @throws
     */
    @SuppressWarnings("rawtypes")
	public static Class getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }
 
    
    /**
     * 
     * @Decripttion 如果给定的bean名字在bean定义中有别名，则返回这些别名
     * @author huangqw
     * @param name bean注册名
     * @return
     * @throws NoSuchBeanDefinitionException[]
     * @date 2015年11月26日上午11:09:03
     * @throws
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }
}
