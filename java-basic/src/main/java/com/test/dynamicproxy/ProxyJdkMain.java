package com.test.dynamicproxy;

public class ProxyJdkMain {

	public static void main(String[] args) {
		UserService userService = new UserServiceImpl();
		/*InvocationHandler invokehandler = new MyInvocationHandler(userService);
		
		UserService target = (UserService)Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), invokehandler);
		*/
		UserService target = (UserService)new MyInvocationHandler(userService).getInstance();
		String proxyresult = target.getName("123");
		System.out.println(proxyresult);
	}
}
