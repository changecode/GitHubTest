package com.java.concurrent.readwritelock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的简单示例
 * @author Tim
 * 在实际开发中，最好在能用读写锁的情况下使用读写锁，而不要用普通锁，以求更好的性能
 */
public class ReadOrWriteLockMain {

	public static void main(String[] args) {
		//创建并发访问的账户 
        MyCount myCount = new MyCount("95599200901215522", 10000); 
        //创建一个锁对象  Lock lock = new ReentrantLock(); 
        ReadWriteLock lock = new ReentrantReadWriteLock(false); 
        //创建一个线程池 
        ExecutorService pool = Executors.newFixedThreadPool(2); 

        User u1 = new User("索大", myCount, 10000, lock, false); 
        User u2 = new User("雪走", myCount, 3000, lock, false); 
        User u3 = new User("鬼彻", myCount, -8000, lock, false); 
        User u4 = new User("和道", myCount, 3000, lock, false); 
        User u5 = new User("秋水", myCount, 5000, lock, true); 
        //在线程池中执行各个用户的操作 
        pool.execute(u1); 
        pool.execute(u2); 
        pool.execute(u3); 
        pool.execute(u4); 
        pool.execute(u5); 
        //关闭线程池 
        pool.shutdown(); 
	}
}
