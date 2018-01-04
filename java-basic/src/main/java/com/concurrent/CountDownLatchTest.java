package com.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

	public static void main(String[] args) {
		
		final CountDownLatch latch = new CountDownLatch(2);
		
		new Thread(){
			@Override
			public void run() {
				try {
					System.out.println("child thread " + Thread.currentThread().getName() + " running...");
					Thread.sleep(3000);
					System.out.println("child thread " + Thread.currentThread().getName() + " work completed");
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				try {
					System.out.println("child thread " + Thread.currentThread().getName() + " running...");
					Thread.sleep(3000);
					System.out.println("child thread " + Thread.currentThread().getName() + " work completed");
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		try {
			System.out.println("waiting two children thread work....");
			latch.await();
			System.out.println("two children thread has worked... ");
			System.out.println("main thread start....");
		} catch (Exception e) {
			
		}
	}
}
