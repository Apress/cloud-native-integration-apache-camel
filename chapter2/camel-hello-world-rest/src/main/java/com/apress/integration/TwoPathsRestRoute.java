package com.apress.integration;

import org.apache.camel.builder.RouteBuilder;

public class TwoPathsRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("platform-http:/twoPaths/helloWorld?httpMethodRestrict=GET")
        .routeId("two-paths-hello")
        .setBody(constant("Hello World"))
        .log("Request responded with body: ${body}");

        from("platform-http:/twoPaths/sayHi?httpMethodRestrict=GET")
        .routeId("two-paths-hi")
        .setBody(constant("Hi"))
        .log("Request responded with body: ${body}");
      
    }
}
