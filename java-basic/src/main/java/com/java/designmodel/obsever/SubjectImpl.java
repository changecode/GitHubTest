package com.java.designmodel.obsever;

import java.util.ArrayList;

public class SubjectImpl implements Subject{

	private ArrayList<Observer> observerList = new ArrayList<Observer>();
	private String info;
	
	@Override
	public void register(Observer obj) {
		observerList.add(obj);
	}

	@Override
	public void remove(Observer obj) {
		int i = observerList.indexOf(obj);
		if(i > 0)
			observerList.remove(i);
	}

	@Override
	public void notifyIfObserverChange() {
		for (int i = 0, len = observerList.size(); i < len; i++) {
			Observer instance = (Observer)observerList.get(i);
			instance.notify(info);
		}
	}

	public void setWork(String _info) {
 		this.info = _info;
		System.out.println("send data: " + info);
		this.notifyIfObserverChange();
	}
}
