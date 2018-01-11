package com.java.designmodel.abstractfactory;

import org.junit.Test;

public class AbstractFactoryTest {

	private SendFactory factory;
	
	@Test
	public void emailSendTest() {
		factory = new EmailFactoryImpl();
		Send instance = factory.instance();
		instance.send();
	}
	
	
	@Test
	public void msgSendTest() {
		factory = new MsnFactoryImpl();
		Send instance = factory.instance();
		instance.send();
	}
}
