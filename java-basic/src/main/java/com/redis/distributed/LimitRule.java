package com.redis.distributed;

public class LimitRule {

	private int seconds;
	private int limitCount;
	private int lockCount;
	private int lockTime;
	
	public boolean enableLimitLock(){
        return getLockTime() > 0 && getLockCount() > 0;
    }
	
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	public int getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}
	public int getLockCount() {
		return lockCount;
	}
	public void setLockCount(int lockCount) {
		this.lockCount = lockCount;
	}
	public int getLockTime() {
		return lockTime;
	}
	public void setLockTime(int lockTime) {
		this.lockTime = lockTime;
	}
	
	
}
