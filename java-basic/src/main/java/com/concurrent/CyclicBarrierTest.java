package com.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
	
	
	public static void main(String[] args) {
		
		final int COUNT = 5;
		
		CyclicBarrier bbb = new CyclicBarrier(COUNT);
		
		/*CyclicBarrier bbb = new CyclicBarrier(COUNT, new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程"+Thread.currentThread().getName());   
            }
        });*/
		
		for(int i = 0; i < COUNT; i++) {
			new Work(bbb).start();
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("##############");
		
		for(int i = 0; i < COUNT; i++) {
			new Work(bbb).start();
		}
		
	}
	
	static class Work extends Thread{
		
		private CyclicBarrier barrier;
		
		public Work(CyclicBarrier _barrier) {
			this.barrier = _barrier;
		}

		@Override
		public void run() {
			
			try {
				System.out.println(Thread.currentThread().getName() + " writing...");
				Thread.sleep(5000);
				System.out.println(Thread.currentThread().getName() + " work thread has writed");
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println("main thread start work");
		}
		
		
	}

}
