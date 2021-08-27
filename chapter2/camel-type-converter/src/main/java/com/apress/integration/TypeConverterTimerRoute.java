package com.apress.integration;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import java.util.UUID;

public class TypeConverterTimerRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("timer:type-converter-timer?period=2000")
            .routeId("type-converter-route")
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    MyObject object = new MyObject();
                    object.setValue(UUID.randomUUID().toString());
                    exchange.getMessage().setBody(object);
                }
            })
            .convertBodyTo(AnotherObject.class)
            .log("${body}");

    }


}
