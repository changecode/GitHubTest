package com.akka.test;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class FirstAkkaDemo extends UntypedActor{

	@Override
	public void preStart() throws Exception {
		final ActorRef greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
		greeter.tell(Greeter.Msg.GREET, getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg == Greeter.Msg.DONE) {
			System.out.println("###");
			getContext().stop(getSelf());
		}else {
			unhandled(msg);
		}
	}

	
}
