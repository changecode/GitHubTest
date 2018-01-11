package com.java.designmodel.abstractfactory;

public class EmailImpl implements Send{

	@Override
	public void send() {
		System.out.println("email send msg");
	}

}
