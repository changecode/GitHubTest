package com.test.dynamicproxy;

import net.sf.cglib.proxy.Enhancer;

public class CglibMain {

	public static void main(String[] args) {
		CglibProxy cglibProxy = new CglibProxy();  
		  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(UserServiceImpl.class);  
        enhancer.setCallback(cglibProxy);  
  
        UserService service = (UserService)enhancer.create();  
        String result = service.getName("cglib");
        System.out.println(result);
	}
}
