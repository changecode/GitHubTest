package com.java.concurrent.reentrantreadwritelock;

/**
 * 写入锁
 * @author Tim
 *
 */
public class Write implements Runnable{

	private PricesInfo pricesInfo;
	
	public Write(PricesInfo _pricesInfo) {
		this.pricesInfo = _pricesInfo;
	}
	
	public void run() {
		for (int i=0; i<3; i++) {
            System.out.printf("Writer: Attempt to modify the prices.\n");
            pricesInfo.setPrices(Math.random()*10, Math.random()*8);
            System.out.printf("Writer: Prices have been modified.\n");
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}

}
