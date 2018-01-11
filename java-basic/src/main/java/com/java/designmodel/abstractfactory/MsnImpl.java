package com.java.designmodel.abstractfactory;

public class MsnImpl implements Send{

	@Override
	public void send() {
		System.out.println("msn send msg");
	}

}
