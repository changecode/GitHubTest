package com.java.rpc;

public class RpcMainTest {

	public static void main(String[] args) throws Exception{
		RpcService service = 
				RpcSimple.refer(RpcService.class, "127.0.0.1", 12306);
		for(int i = 0; i < 10; i++){
			System.out.println(service.printHello(i));
			Thread.sleep(1000);
		}
	}
}
