package com.java.productconsumer;

public class Product extends Thread{

	private int productNumber;
	private Godown godown;
	
	public Product(int productNum, Godown god) {
		this.productNumber = productNum;
		this.godown = god;
	}
	
	@Override
	public void run() {
		godown.product(productNumber);
	}
}
