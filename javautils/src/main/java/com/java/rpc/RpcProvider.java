package com.java.rpc;

public class RpcProvider {

	public static void main(String[] args) throws Exception {
		RpcService service = new RpcInterfaceImpl();
		RpcSimple.exportServer(service, 12306);
	}
}
