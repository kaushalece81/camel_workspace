package com.example.camelmicroservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//@Component
public class MyFirstTimerRouter extends RouteBuilder{

	@Autowired
	private GetCurentTimeBean getCurentTimeBean;
	
	@Autowired
	private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;
	
	@Override
	public void configure() throws Exception {
		//queue
		//transformation
		//database
		//but for easiness we will use
		
		//timer to trigger messages
		// transformation
		//log
		// Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		//Exchange[ExchangePattern: InOnly, BodyType: String, Body: My constant message:]
		//Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time now is : 2022-03-07T12:14:56.827821100]
//		Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time now is : 2022-03-07T12:18:45.104646100]
//		Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time now is : 2022-03-07T12:18:46.105036100]
//		Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time now is : 2022-03-07T12:18:47.105157100]
		from("timer:first-timer")   // timer is keyword
		.log("${body}")
		.transform().constant("My constant message: ")  // add body message
		.log("${body}")
		//.transform().constant("Time now is : "+ LocalDateTime.now())  // add body message time now  bit time is not changing is same always
		//.bean("getCurentTimeBean")  // use spring bean for transformation now the time changes in every log  // hardcoding of bean name is bad practive here
		// should use autowired
		.bean(getCurentTimeBean)  // note method name is not configured here
		.bean(getCurentTimeBean,"getCurrentTimeNow") // if more than one method bean then we need method to be specified
		.log("${body}")
		// processing if we do not change the body of message its processing
		.bean(simpleLoggingProcessingComponent)  // no change in body hence process happening
		.log("${body}")
		.process(new SimpleLoggingProcessor()) // processor
		.to("log:first-timer") ; // log is keyword
		
		
		
		// Transformation if we change the body of message its transformation
		//.transform().constant("My constant message: ")
		//.bean(getCurentTimeBean) 
		//.bean(getCurentTimeBean,"getCurrentTimeNow")
		
	}

}
@Component
class GetCurentTimeBean{
	public String getCurrentTimeNow() {
		return "Time now is : "+ LocalDateTime.now();
	}
}
@Component
class SimpleLoggingProcessingComponent{
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponent {} ", message);
	}
}


@Component
class SimpleLoggingProcessor implements Processor{
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessor {} ", exchange.getMessage().getBody());
	}
}
