package com.apress.integration;

import org.apache.camel.builder.RouteBuilder;

public class HelloWorldRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:example?period=2000")
        .setBody(constant("Hello World"))
        .to("log:" + HelloWorldRoute.class.getName() );
    }
}
