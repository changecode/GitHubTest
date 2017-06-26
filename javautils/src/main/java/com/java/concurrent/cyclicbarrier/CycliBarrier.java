package com.java.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * CycliBarrier 示例
 * 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 * 在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。
 * 因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 * CyclicBarrier 支持一个可选的 Runnable 命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），该命令只在每个屏障点运行一次
 * @author Tim
 *
 */
public class CycliBarrier {

	private static CyclicBarrier barrier;

	static class ThreadTest1 extends Thread {
		public void run() {
			System.out.println(Thread.currentThread().getName() + "达到...");
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "执行完成...");
		}
	}

	public static void main(String[] args) {
		barrier = new CyclicBarrier(5, new Runnable() {

			public void run() {
				System.out.println("执行CyclicBarrier中的任务.....");
			}
		});

		for (int i = 1; i <= 5; i++) {
			new ThreadTest1().start();
		}
	}
}
