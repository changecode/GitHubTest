package com.java.concurrent.reentrantreadwritelock;

/**
 * 读取锁
 * @author Tim
 *
 */
public class Reader implements Runnable{
	
	private PricesInfo pricesInfo;
	
	public Reader(PricesInfo _pricesInfo) {
		this.pricesInfo = _pricesInfo;
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "--Price 1:" + pricesInfo.getPrice1());
            System.out.println(Thread.currentThread().getName() + "--Price 1:" + pricesInfo.getPrice2());
        }
	}

}
