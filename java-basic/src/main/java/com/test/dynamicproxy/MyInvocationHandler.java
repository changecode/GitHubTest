package com.test.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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

}
