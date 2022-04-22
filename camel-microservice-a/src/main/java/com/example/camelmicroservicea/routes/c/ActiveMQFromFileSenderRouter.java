package com.example.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class ActiveMQFromFileSenderRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		//read from file json folder copy any file into json folder
		//queue
		from("file:files/json")
		.log("${body}")
		.to("activemq:my-activemq-queue"); 	
	}
}
