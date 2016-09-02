package com.jngld.utils;

import com.jngld.utils.exception.ParameterException;
import com.jngld.utils.exception.ReflectException;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;


/**
 * 非空判断工具类
 * @author wangzz-a
 * @version $Id: EmptyUtil.java, v 0.1 2015年11月25日 下午1:44:04 wangzz-a Exp $
 */
public class EmptyUtil {

	/**私有化构造函数*/
	private EmptyUtil(){}

	/**
	 * 返回对象是否不为空
	 * <br>
	 * 为空返回false,非空返回true
	 * @author wangzz-a
	 * @param obj
	 */
	public static boolean isNotEmpty(Object obj){
		if(isNull(obj)){
			return false;
		}
		if(obj.getClass().isArray()){
			return Array.getLength(obj) > 0 ? true : false;
		}
		if(obj instanceof Collection){
			return ((Collection<?>) obj).size() > 0 ? true : false;
		}
		if(obj instanceof Map){
			return ((Map<?, ?>) obj).size() > 0 ? true : false;
		}
		if(obj instanceof String){
			return obj.toString().trim().length() > 0 ? true : false;
		}
		if(obj instanceof File){
			return ((File) obj).exists();
		}
		if(obj instanceof Boolean){
			return (Boolean) obj;
		}
		return true;
	}
	/**
	 * 返回对象是否为空
	 * <br>
	 * 为空返回true,非空返回false
	 * @author wangzz-a
	 * @param obj
	 * @return
	 * @date 2015年11月25日 下午1:52:56
	 */
	public static boolean isEmpty(Object obj){
		return ! isNotEmpty(obj);
	}

	public static boolean isNull(Object obj){
		return obj == null ? true : false;
	}

	public static boolean isNotNull(Object obj){
		return !isNull(obj);
	}

	/**
	 *
	 * @Description  是否含有指定空字段
	 * @author youps-a
	 * @date 2015年12月12日 上午11:02:26
	 * @param object   需校验的对象
	 * @param methods  需校验的方法名数组
	 * @return         如果所有指定方法名的值均不为空，返回true
	 * @throws ReflectException 反射方法异常
	 */
	public static boolean isNotHaveEmpty(Object object,String[] methods) throws ReflectException{
	    if (isEmpty(object) || isEmpty(methods)) {
            throw new ParameterException(ParameterException.message);
        }
        for (String methodName : methods) {
            Object value = ReflectUtil.get(object, methodName);
            if (isEmpty(value)) {
                return false;
            }
        }
	    return true;
    }
}
