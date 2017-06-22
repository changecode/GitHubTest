package com.java.concurrent.countdownLatch;

public class Participater implements Runnable {

	private String name;
	private Conference conference;

	public Participater(String name, Conference conference) {
		this.name = name;
		this.conference = conference;
	}

	public void run() {
		conference.arrive(name);
		
	}
}
