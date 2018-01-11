package com.java.designmodel.singleton;

public class SingletonTest {

	private SingletonTest() {}
	
	private static SingletonTest instance = null;
	
	/** 1
	 * unsafe thread instance
	 * @return
	*/
	public static SingletonTest getInstance() {
		if(instance == null) {
			System.out.println("#######I am created######");
			instance = new SingletonTest();
		}
		return instance;
	} 
	
	/** 2
	 * safe thread instance
	 * @return
	 
	public static synchronized SingletonTest getInstance() {
		if(instance == null) {
			System.out.println("#######I am created######");
			instance = new SingletonTest();
		}
		return instance;
	}*/
	
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					SingletonTest.getInstance();
				}
			}).start();
		}
		
	}
	
}
