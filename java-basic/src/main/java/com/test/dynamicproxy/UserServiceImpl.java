package com.test.dynamicproxy;

public class UserServiceImpl implements UserService{

	@Override
	public String getName(String msg) {
		System.out.println("####getName####");
		return msg;
	}

}
