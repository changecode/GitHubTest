package com.java.designmodel.abstractfactory;

public class EmailFactoryImpl implements SendFactory{

	@Override
	public Send instance() {
		return new EmailImpl();
	}

}
