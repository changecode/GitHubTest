package com.java.designmodel.obsever;

public class Main {

	public static void main(String[] args) {
		
		SubjectImpl sub = new SubjectImpl();
		new ObserverImpl(sub, "t1");
		new ObserverImpl(sub, "t2");
		new ObserverImpl(sub, "t3");
		
		sub.setWork("123");
	}
}
