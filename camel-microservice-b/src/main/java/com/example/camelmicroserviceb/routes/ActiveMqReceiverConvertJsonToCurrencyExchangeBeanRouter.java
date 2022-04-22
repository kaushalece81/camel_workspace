package com.example.camelmicroserviceb.routes;

import java.math.BigDecimal;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
@Component
public class ActiveMqReceiverConvertJsonToCurrencyExchangeBeanRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("activemq:my-activemq-queue")
		//read Body: { "id": 1000, "from": "USD", "to": "INR", "conversionMultiple": 70 }]
		// convert JSON to CurrencyExchangeBean
		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
		.to("log:received-message-from-active-mq");
		
	}

}
class CurrencyExchange{
	private Long id;
	private String from;
	private String to;
	private BigDecimal conversionMultiple;
	public CurrencyExchange() {
	}
	public CurrencyExchange(Long id, String from, String to, BigDecimal conversionMultiple) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}
	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}
	@Override
	public String toString() {
		return String.format("CurrencyExchange [id=%s, from=%s, to=%s, conversionMultiple=%s]", id, from, to,
				conversionMultiple);
	}
	
}