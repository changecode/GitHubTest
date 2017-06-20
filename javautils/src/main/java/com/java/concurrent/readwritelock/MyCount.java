package com.java.concurrent.readwritelock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyCount {

	private String oid; // 账号
	private int cash; // 账户余额
	private Lock lock = new ReentrantLock(); // 账户锁
	private Condition _save = lock.newCondition(); // 存款条件
	private Condition _draw = lock.newCondition(); // 取款条件

	public MyCount(String oid, int cash) {
		this.oid = oid;
		this.cash = cash;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	@Override
	public String toString() {
		return "MyCount{" + "oid='" + oid + '\'' + ", cash=" + cash + '}';
	}

	/**
	 * 取款
	 * @param x
	 * @param name
	 */
	public void drawing(int x, String name) {
		lock.lock(); // 获取锁
		try {
			if (cash - x < 0) {
				_draw.await(); // 阻塞取款操作
			} else {
				cash -= x; // 取款
				System.out.println(name + "取款" + x + "，当前余额为" + cash);
			}
			_save.signalAll(); // 唤醒所有存款操作
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock(); // 释放锁
		}
	}

	/**
	 * 存款
	 * @param x
	 * @param name
	 */
	public void saving(int x, String name) {
		lock.lock(); // 获取锁
		if (x > 0) {
			cash += x; // 存款
			System.out.println(name + "存款" + x + "，当前余额为" + cash);
		}
		_draw.signalAll(); // 唤醒所有等待线程。
		lock.unlock(); // 释放锁
	}
}
