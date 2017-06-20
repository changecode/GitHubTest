package com.java.concurrent.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.java.concurrent.readwritelock.MyCount;

/**
 * 条件变量都实现了java.util.concurrent.locks.Condition接口，
 * 条件变量的实例化是通过一个Lock对象上调用newCondition()方法来获取的，这样，条件就和一个锁对象绑定起来了。
 * 因此，Java中的条件变量只能和锁配合使用，来控制并发程序访问竞争资源的安全
 * 
 * @author Tim ==================== 业务场景
 *         有一个账户，多个用户（线程）在同时操作这个账户，有的存款有的取款，存款随便存，取款有限制，不能透支，
 *         任何试图透支的操作都将等待里面有足够存款才执行操作
 */
public class ConditionMainTest {

	public static void main(String[] args) {
		MyCount myCount = new MyCount("95599200901215522", 10000);
		// 创建一个线程池
		ExecutorService pool = Executors.newFixedThreadPool(2);
		Thread t1 = new SaveThread("索大", myCount, 2000);
		Thread t2 = new SaveThread("雪走", myCount, 3600);
		Thread t3 = new DrawThread("鬼泣", myCount, 2700);
		Thread t6 = new DrawThread("秋水", myCount, 800);
		// 执行各个线程
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t6);
		// 关闭线程池
		pool.shutdown();
	}
}
