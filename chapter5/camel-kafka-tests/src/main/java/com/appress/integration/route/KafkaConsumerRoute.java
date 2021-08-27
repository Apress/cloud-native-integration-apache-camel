package com.appress.integration.route;

import org.apache.camel.builder.RouteBuilder;

public class KafkaConsumerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("{{kafka.uri.from}}")
        .routeId("consumer-route")
        .log("Headers : ${headers}")
        .to("{{final.endpoint}}");

    }
}