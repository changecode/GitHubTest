package com.java.productconsumer;

/**
 * 消费者-生产者模型中的仓库角色
 * @author Tim
 *
 */
public class Godown {

	/**仓库最大容量*/
	private static final int MAX_NUM = 100;
	/**仓库当前容量*/
	private int currentNum;
	
	public Godown(int num) {
		this.currentNum = num;
	}
	
	/**
	 * 生产指定数量的商品
	 * @param num
	 */
	public synchronized void product(int productNum) {
		while(productNum + currentNum > MAX_NUM) {
			System.out.println("要生产的数量和仓库剩余的量和大于仓库最大容量");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		currentNum += productNum;
		System.out.println("已经生产了 " + productNum + " 现有仓库剩余： " + currentNum);
		notifyAll();
	}
	
	/**
	 * 消费指定数量的商品
	 * @param consumerNum
	 */
	public synchronized void consumer(int consumerNum) {
		while( currentNum < consumerNum) {
			System.out.println("需要消费" + consumerNum + " 仓库目前剩余数量" + currentNum + " 仓库剩余数量不够消费");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		currentNum -= consumerNum;
		System.out.println("已经消费了  " + consumerNum + " 仓库剩余 " + currentNum);
		notifyAll();
	}
}
