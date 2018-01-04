package com.concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	public static void main(String[] args) {
		
		int num = 9;
		Semaphore phore = new Semaphore(3);
		
		for(int i = 0; i < num; i++) {
			new Work(i, phore).start();
		}
	}
	
	
	static class Work extends Thread{
		
		private int num;
		private Semaphore semaphore;
		
		public Work(int num, Semaphore semaphore) {
			this.num = num;
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.println("worker-" + this.num + " acquire machine");
				System.out.println("########");
				Thread.sleep(3000);
				System.out.println("replease-" + this.num + " replease machine");
				semaphore.release();
			} catch (Exception e) {

			}
			
		}
		
		
	}
}
