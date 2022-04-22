package com.example.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
//@Component
public class ActiveMQSenderRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		//timer  per 10 seconds
		//queue
		from("timer:active-mq-timer?period=10000")
		.transform().constant("My message for Active MQ")
		.log("${body}")
		.to("activemq:my-activemq-queue"); //error queue is not created my-activemq-queue 
		// Error in logs Failed to create route route2 at: >>> To[activemq:my-activemq-queue] <<< in route: Route(route2)
		// camel-activemq-starter dependency to be added in pom.xml
		
	}

}
