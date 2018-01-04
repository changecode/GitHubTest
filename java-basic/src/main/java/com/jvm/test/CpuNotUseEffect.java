package com.jvm.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CpuNotUseEffect {

	private static final int count = 200;
	private static final int threadCount = 10;
	
	private static final int TASK_COUNT = 16;
	private static CountDownLatch latch;
	
	public static void main(String[] args) throws Exception {
		/*Task t = new Task();
		for(int i = 0; i < count; i++) {
			t.addTasks(String.valueOf(i));
		}*/
		Task[] tasks = new Task[TASK_COUNT];  
        for (int i = 0; i < TASK_COUNT; i++) {  
            tasks[i] = new CpuNotUseEffect().new Task(); 
        }  
        for (int i = 0; i < count; i++) {  
            int mod = i % TASK_COUNT;  
            tasks[mod].addTasks(Integer.toString(i));  
        }  
          
		
		Long start = System.currentTimeMillis();
		for(int j = 0; j < threadCount; j++) {
			/*
			Thread thread = new Thread(t);
			thread.start();
			thread.join();*/
			System.out.println("Thread-"+j);
			latch = new CountDownLatch(TASK_COUNT);
			for(int k = 0; k < TASK_COUNT; k++) {
				Thread t = new Thread(tasks[j]);
				t.start();
			}
			latch.await();
		}
		System.out.println("speed times: " + (System.currentTimeMillis() - start));
	}
	
	
	
	class Task implements Runnable{

		List<String> task = new ArrayList<String>();
		Random random = new Random();
		
		public void addTasks(String taskParam) {
			task.add(taskParam);
		}
		
		@Override
		public void run() {
			List<String> result = task;
			List<String> rrr = new ArrayList<String>();
			
			for(String str : result) {
				try {
					Thread.sleep(random.nextInt(10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rrr.add(str);
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			latch.countDown();
		}
		
	}
	
}


