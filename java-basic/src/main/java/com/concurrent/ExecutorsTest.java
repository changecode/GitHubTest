package com.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsTest {

	
	private static int threadCount = 10000;
	
	public static void main(String[] args) {
		
		long startFixed = System.currentTimeMillis();
		fixedThreadPoolTest(3);
		System.out.println("######fixedThreadPoolTest######: " + (System.currentTimeMillis() - startFixed));
		long single = System.currentTimeMillis();
		singleThreadPoolTest();
		System.out.println("##########singleThreadPoolTest##########:" + (System.currentTimeMillis() - single));
//		long cache = System.currentTimeMillis();
//		cacheThreadPoolTest();
//		System.out.println("#############cacheThreadPoolTest############" + (System.currentTimeMillis() - cache));
//		scheduleThreadPoolTest(5);
		System.exit(0);
		
	}
	
	/**
	 * execute task per schedule
	 */
	private static void scheduleThreadPoolTest(int threadCount) {
		ScheduledExecutorService  exe = Executors.newScheduledThreadPool(threadCount);
		exe.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getId() + " executing...");
			}
		}, 0, 3, TimeUnit.SECONDS);
		
	}
	
	/**
	 * 60 seconds auto release thread
	 */
	private static void cacheThreadPoolTest() {
		ExecutorService exe  = Executors.newCachedThreadPool();
		for(int i = 0; i < threadCount; i++) {
			exe.execute(getThread(i));
		}
		
	}
	
	
	/**
	 * singleThreadPool(1)
	 */
	private static void singleThreadPoolTest() {
		ExecutorService exe = null;
		try {
			exe = Executors.newSingleThreadExecutor();
			for(int i = 0; i < threadCount; i++) {
				exe.execute(getThread(i));
			}
		} finally{
			exe.shutdown();
		}
	}
	
	/**
	 * init fixedthreadPool size(5)
	 */
	private static void fixedThreadPoolTest(int threadCount) {
		ExecutorService exe = null;
		try {
			exe = Executors.newFixedThreadPool(threadCount);
			for(int i = 0; i < threadCount; i++) {
				exe.execute(getThread(i));
			}
		} finally {
			exe.shutdown();
		}
	}
	
	
	private static Runnable getThread(final int i) {
		
		return new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
//				System.out.println(i);
			}
		}; 
	}
}
