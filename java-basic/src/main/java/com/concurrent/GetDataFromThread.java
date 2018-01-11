package com.concurrent;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

public class GetDataFromThread {

	
	private static final ConcurrentMap<Thread, Integer> threadMap = new ConcurrentHashMap<Thread, Integer>();//Maps.newConcurrentMap();
//	private static final HashMap<Thread, Integer> threadMap = new HashMap<Thread, Integer>();
	
//	private static final ThreadLocal<Integer> threadlocal = new ThreadLocal<Integer>();
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public  void run() {
					int random = new Random().nextInt(100);
					threadMap.put(Thread.currentThread(), random);
//					threadlocal.set(random);
					
					new ModuleA().moduleAGetDataFromThread();
					new ModuleB().moduleBGetDataFromThread();
				}
			}).start();
		}
	}
	
	
	static class ModuleA {
		
		public  void moduleAGetDataFromThread() {
			System.out.println("ModuleA 从线程" + Thread.currentThread() + "获取到的int值: " + threadMap.get(Thread.currentThread()));
//			System.out.println("ModuleA 从线程" + Thread.currentThread() + "获取到的int值: " + threadlocal.get());
		}
	}
	
	static class ModuleB {
		
		public  void moduleBGetDataFromThread() {
			System.out.println("ModuleB 从线程" + Thread.currentThread() + "获取到的int值: " + threadMap.get(Thread.currentThread()));
//			System.out.println("ModuleB 从线程" + Thread.currentThread() + "获取到的int值: " + threadlocal.get());
		}
	}
}
