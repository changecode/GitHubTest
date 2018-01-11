package com.java.designmodel.obsever;

public class ObserverImpl implements Observer{

	private SubjectImpl subject;
	private String observerName;
	
	
	public ObserverImpl(SubjectImpl subject, String observerName) {
		this.subject = subject;
		this.observerName = observerName;
		subject.register(this);
	}


	@Override
	public void notify(String info) {
		System.out.println("observer " + this.observerName + " receive data: " + info);
	}

}
