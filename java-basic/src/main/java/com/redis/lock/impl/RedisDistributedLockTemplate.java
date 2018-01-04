package com.redis.lock.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redis.lock.Callback;
import com.redis.lock.DistributedLockTemplate;

import redis.clients.jedis.JedisPool;

public class RedisDistributedLockTemplate implements DistributedLockTemplate{

	private static final Logger log = LoggerFactory.getLogger(RedisDistributedLockTemplate.class);
	
	private JedisPool jedisPool;
	
	
	public RedisDistributedLockTemplate(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}



	@Override
	public Object execute(String lockId, int timeout, Callback callback) {

		RedisReentrantLock  distributedReentrantLock  = null;
		boolean getLock = false;
		try {
			distributedReentrantLock = new RedisReentrantLock(jedisPool, lockId);
			if(distributedReentrantLock.tryLock(new Long(timeout), TimeUnit.MILLISECONDS)) {
				getLock = true;
				return callback.onGetLock();
			}else {
				return callback.onTimeout();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			if(getLock) {
				distributedReentrantLock.unlock();
			}
		}
		
		
		return null;
	}

}
