package com.redis.lock;

public interface Callback {

	public Object onGetLock() throws InterruptedException;
	
	public Object onTimeout() throws InterruptedException;
}
