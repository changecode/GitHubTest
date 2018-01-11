package com.java.designmodel.obsever;

public interface Subject {

	/**
	 * register observer
	 */
	public void register(Observer obj);
	
	/**
	 * remove observer
	 */
	public void remove(Observer obj);
	
	public void notifyIfObserverChange();
	
}
