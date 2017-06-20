package com.java.concurrent.condition;

import com.java.concurrent.readwritelock.MyCount;

/**
 * 存款操作
 * @author Tim
 *
 */
public class SaveThread extends Thread {

	private String name; // 操作人
	private MyCount myCount; // 账户
	private int x; // 存款金额

	SaveThread(String name, MyCount myCount, int x) {
		this.name = name;
		this.myCount = myCount;
		this.x = x;
	}

	public void run() {
		myCount.saving(x, name);
	}
}
