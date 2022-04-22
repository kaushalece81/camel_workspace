package com.example.camelmicroservicea.routes.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFileRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// move files from input folder to output folder
		// refresh project then input folder is created
		// immediately input folder content is moved to output folder
		from("file:files/input")
		.log("${body}")
		.to("file:files/output");
		
	}

}
