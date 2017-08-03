package com.java.rpc;

public class RpcInterfaceImpl implements RpcService{

	@Override
	public int printHello(int msg) {
		return msg;
	}

}
