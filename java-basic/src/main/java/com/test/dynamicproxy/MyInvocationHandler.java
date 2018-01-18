package com.test.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler{
	
	private Object target;
	
	public MyInvocationHandler(){
		super();
	}
	
	public MyInvocationHandler(Object target_) {
		super();
		this.target = target_;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = method.invoke(target, args); 
            return result;
	}

	public Object getInstance() {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}
}
