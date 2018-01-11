package com.test.dynamicproxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

	@Override
	public Object intercept(Object target, Method method, Object[] params, MethodProxy methodproxy) throws Throwable {
		return methodproxy.invokeSuper(target, params);
	}

}
