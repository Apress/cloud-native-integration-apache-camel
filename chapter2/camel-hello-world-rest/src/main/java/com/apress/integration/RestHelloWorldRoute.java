package com.apress.integration;

import org.apache.camel.builder.RouteBuilder;

public class RestHelloWorldRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("platform-http:/helloWorld?httpMethodRestrict=GET")
        .routeId("rest-hello-world")
        .setBody(constant("Hello World"))
        .log("Request responded with body: ${body}");
      
    }
}
