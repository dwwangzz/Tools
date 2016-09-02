package com.jngld.utils;

import java.lang.reflect.Field;

import com.jngld.utils.exception.ParameterException;
import com.jngld.utils.exception.ReflectException;


/**
 * 
 * @Description 反射方法工具类
 * @author (作者) youps-a
 * @date (开发日期) 2015年12月12日 上午11:22:22
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class ReflectUtil {
    
    private ReflectUtil() {
    }
    
    /**
     * 
     * @Description 反射get方法取值
     * @author youps-a
     * @date 2015年12月12日 上午11:33:27
     * @param object        被取值的对象
     * @param methodName    属性名
     * @return              对象对应属性值
     * @throws ReflectException 反射方法异常
     */
    public static Object get(Object object,String methodName) throws ReflectException{
        if (EmptyUtil.isEmpty(object) || EmptyUtil.isEmpty(methodName)) {
            throw new ParameterException(ParameterException.message);
        }
        Object value = null;
            Field field;
            try {
                field = object.getClass().getDeclaredField(methodName);
                field.setAccessible(true);
                value = field.get(object);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new ReflectException(ReflectException.message);
            } catch (SecurityException e) {
                e.printStackTrace();
                throw new ReflectException(ReflectException.message);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new ReflectException(ReflectException.message);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new ReflectException(ReflectException.message);
            }
        return value;
    }
    
    /**
     * 
     * @Description 反射set方法赋值
     * @author youps-a
     * @date 2015年12月12日 上午11:34:48
     * @param object        被赋值对象
     * @param methodName    属性名
     * @param value         赋值内容
     * @throws ReflectException 反射方法异常
     */
    public static void set(Object object,String methodName,Object value) throws ReflectException{
        if (EmptyUtil.isEmpty(object) || EmptyUtil.isEmpty(methodName)) {
            throw new ParameterException(ParameterException.message);
        }
        try {
            Field field = object.getClass().getDeclaredField(methodName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new ReflectException(ReflectException.message);
        } catch (SecurityException e) {
            e.printStackTrace();
            throw new ReflectException(ReflectException.message);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ReflectException(ReflectException.message);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new ReflectException(ReflectException.message);
        }
    }

}
