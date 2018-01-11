package com.test.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyJdkMain {

	public static void main(String[] args) {
		UserService userService = new UserServiceImpl();
		InvocationHandler invokehandler = new MyInvocationHandler(userService);
		
		UserService proxy = (UserService)Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), invokehandler);
		String proxyresult = proxy.getName("123");
		System.out.println(proxyresult);
	}
}
