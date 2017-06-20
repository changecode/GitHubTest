package com.java.concurrent.condition;

import com.java.concurrent.readwritelock.MyCount;

/**
 * 取款操作 
 * @author Tim
 *
 */
public class DrawThread extends Thread {

	private String name; // 操作人
	private MyCount myCount; // 账户
	private int x; // 存款金额

	DrawThread(String name, MyCount myCount, int x) {
		this.name = name;
		this.myCount = myCount;
		this.x = x;
	}

	public void run() {
		myCount.drawing(x, name);
	}
}
