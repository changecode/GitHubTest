package com.jvm.test;

public class GetClassCountTest {

	public static void main(String[] args) {
		
		System.out.println("availableProcessors: "+ Runtime.getRuntime().availableProcessors());
		System.out.println("totalMemory: "+ Runtime.getRuntime().totalMemory());
		System.out.println("freeMemory: "+ Runtime.getRuntime().freeMemory());
		System.out.println("maxMemory: "+ Runtime.getRuntime().maxMemory());
		
	}
	
}
