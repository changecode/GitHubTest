package com.akka.test;

import com.alibaba.fastjson.JSONObject;

import akka.actor.UntypedActor;

public class Greeter extends UntypedActor{

	public static enum Msg {
		GREET, DONE;
	}

	/*@Override
	public void onReceive(Object msg) throws Exception {
	
		if(msg == Msg.GREET) {
			System.out.println("hello akka");
			Thread.sleep(1000);
			getSender().tell(Msg.DONE, getSelf());
		}else {
			unhandled(msg);
		}
	}*/
	
	@Override
	public void onReceive(Object msg) throws Exception {
		
		System.out.println("greet recevice data: " + JSONObject.toJSONString(msg));
		getSender().tell("greet work compeleted", getSelf());
	}
	

}
