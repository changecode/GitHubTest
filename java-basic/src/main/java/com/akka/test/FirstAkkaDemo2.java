package com.akka.test;

import java.util.Arrays;

import com.alibaba.fastjson.JSONObject;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class FirstAkkaDemo2 extends UntypedActor{

	ActorRef greeter;
	
	@Override
	public void preStart() throws Exception {
		greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
		System.out.println("greeter actor path: " + greeter.path());
		greeter.tell(new Message(2, Arrays.asList("2","final actorRef")), getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println("FirstAkkaDemo2 receive data: " + JSONObject.toJSONString(msg));
	}

	
}
