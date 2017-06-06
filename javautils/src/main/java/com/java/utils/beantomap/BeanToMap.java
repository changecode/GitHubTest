package com.java.utils.beantomap;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bean 与 map之间的转换
 * @author Tim
 *
 */
public class BeanToMap {

	private static final Logger log = LoggerFactory.getLogger(BeanToMap.class);
	
	/**
	 * 将bean转换成map对象
	 * @param object
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object object) {
		
		if(object == null){
			return null;
		}
		Map<String, Object> mapResult = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(object);
                    mapResult.put(key, value);
                }
            }
		} catch (Exception e) {
			if(log.isErrorEnabled()) {
				log.error("bean to map errror", e);
			}
		}
		return mapResult;
	}
	
	/**
	 * map转换成object
	 * @param paramMap
	 * @param object
	 * @return
	 */
	public static Object mapToObject(Map<Object, Object> paramMap, Object object) {
		
		if(paramMap == null || object == null){
			return null;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (paramMap.containsKey(key)) {
                    Object value = paramMap.get(key);
                    Method setter = property.getWriteMethod();
                    setter.invoke(object, value);
                }
            }
		} catch (Exception e) {
			if(log.isErrorEnabled()) {
				log.error("map to object error", e);
			}
		}
		return object;
	}
	
}
