package com.java.concurrent.reentrantreadwritelock;

/**
 * 读写锁简单示例
 * @author Tim
 *
 */
public class Test {

	public static void main(String[] args) {
		PricesInfo pricesInfo = new PricesInfo();

		Reader[] readers = new Reader[5];
		Thread[] readerThread = new Thread[5];
		for (int i = 0; i < 5; i++) {
			readers[i] = new Reader(pricesInfo);
			readerThread[i] = new Thread(readers[i]);
		}

		Write writer = new Write(pricesInfo);
		Thread threadWriter = new Thread(writer);

		for (int i = 0; i < 5; i++) {
			readerThread[i].start();
		}
		threadWriter.start();
	}
}
