package com.apress.integration.route;

import org.apache.camel.builder.RouteBuilder;

public class DeadLetterRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("seda:dead-letter")
        .routeId("dlq-route")
        .log("Problem with request \"${body}\"");

    }

}
