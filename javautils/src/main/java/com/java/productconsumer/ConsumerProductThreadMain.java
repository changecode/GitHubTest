package com.java.productconsumer;

public class ConsumerProductThreadMain {

	public static void main(String[] args) {
		
		Godown godown = new Godown(30); 
        Consumer c1 = new Consumer(40, godown); 
        Consumer c2 = new Consumer(30, godown); 
        Consumer c3 = new Consumer(30, godown); 
        Product p1 = new Product(10, godown); 
        Product p2 = new Product(10, godown); 
        Product p3 = new Product(10, godown); 
        Product p4 = new Product(10, godown); 
        
        c1.start();
        c2.start();
        c3.start();
        
        p1.start();
        p2.start();
        p3.start();
        p4.start();
	}
}
