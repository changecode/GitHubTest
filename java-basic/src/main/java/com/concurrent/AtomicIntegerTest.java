package com.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

	public static void main(String[] args) throws InterruptedException {
		AtomicIntegerThread test = new AtomicIntegerThread();
		Thread t1 = new Thread(test);
		Thread t2 = new Thread(test);
		t1.start();
		t2.start();
		Thread.sleep(1000);
		System.out.println(test.atomicInt.get());
//		System.out.println(test.atomicInt);
	}
}



class AtomicIntegerThread implements Runnable {

	AtomicInteger atomicInt = new AtomicInteger(0);
//	int atomicInt = 0;
	
	@Override
	public void run() {
		for(int i = 0; i < 10000; i++) {
			atomicInt.getAndIncrement();
//			atomicInt += i;
		}
	}
	
}
