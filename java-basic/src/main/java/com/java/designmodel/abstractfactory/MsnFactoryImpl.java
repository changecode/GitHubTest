package com.java.designmodel.abstractfactory;

public class MsnFactoryImpl implements SendFactory{

	@Override
	public Send instance() {
		return new MsnImpl();
	}

}
