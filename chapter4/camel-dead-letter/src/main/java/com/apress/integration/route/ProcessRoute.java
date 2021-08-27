package com.apress.integration.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class ProcessRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        errorHandler(deadLetterChannel("seda:dead-letter")
                .maximumRedeliveries(1)
                .useOriginalMessage()
                .onExceptionOccurred(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        log.error("Exception Message : "+exchange.getException().getMessage());
                        log.error("Current body: \""+exchange.getMessage().getBody()+"\"");
                    }
                }) );

        from("seda:process-route")
        .routeId("process-route")
        .bean("exceptionBean")
        .log("Message Processed with body : ${body}");


    }
}
