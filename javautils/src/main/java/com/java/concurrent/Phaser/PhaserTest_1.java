package com.java.concurrent.Phaser;

import java.util.concurrent.Phaser;

/**
 * 每个Phaser实例都会维护一个phase number，初始值为0。每当所有注册的任务都到达Phaser时，
 * phase number累加，并在超过Integer.MAX_VALUE后清零。arrive()和arriveAndDeregister()方法用于记录到达；
 * 其中arrive()，某个参与者完成任务后调用；arriveAndDeregister()，任务完成，取消自己的注册。
 * arriveAndAwaitAdvance()，自己完成等待其他参与者完成，进入阻塞，直到Phaser成功进入下个阶段
 * 
 * @author Tim
 *
 */
public class PhaserTest_1 {

	public static void main(String[] args) {
		Phaser phaser = new Phaser(5);

		for (int i = 0; i < 5; i++) {
			Task_01 task_01 = new Task_01(phaser);
			Thread thread = new Thread(task_01, "PhaseTest_" + i);
			thread.start();
		}
	}

	static class Task_01 implements Runnable {
		private final Phaser phaser;

		public Task_01(Phaser phaser) {
			this.phaser = phaser;
		}

		public void run() {
			System.out.println(Thread.currentThread().getName() + "执行任务完成，等待其他任务执行......");
			// 等待其他任务执行完成
			phaser.arriveAndAwaitAdvance();
			System.out.println(Thread.currentThread().getName() + "继续执行任务...");
		}
	}
}
