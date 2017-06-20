package com.java.productconsumer;

public class Consumer extends Thread{

	private int consumerNumber;
	private Godown godown;
	
	public Consumer(int consumerNum, Godown god) {
		this.consumerNumber = consumerNum;
		this.godown = god;
	}
	
	@Override
	public void run() {
		godown.consumer(consumerNumber);
	}
}
