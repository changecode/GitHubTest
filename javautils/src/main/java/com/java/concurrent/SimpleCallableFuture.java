package com.java.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 可返回值的任务必须实现Callable接口
 *	执行Callable任务后，可以获取一个Future的对象，在该对象上调用get就可以获取到Callable任务返回的Object了
 * @author Tim
 *
 */
public class SimpleCallableFuture {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService service = Executors.newFixedThreadPool(2);
		Callable<Object> call_1 = new CallableTest("task_1");
		Callable<Object> call_2 =  new CallableTest("task_2");
		
		Future<Object> future_1 = service.submit(call_1);
		Future<Object> future_2 = service.submit(call_2);
		
		System.out.println(future_1.get().toString() + "..." + future_2.get().toString());
		service.shutdown();
	}
	
}

class CallableTest implements Callable<Object> {

	private String obj;
	
	public CallableTest(String _obj) {
		this.obj = _obj;
	}
	
	public Object call() throws Exception {
		return obj + "返回的任务，执行的结果";
	}
	
}
