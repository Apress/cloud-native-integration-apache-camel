package com.appress.integration.route;

import org.apache.camel.builder.RouteBuilder;

public class KafkaConsumerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

      from("{{kafka.uri}}")
      .routeId("consumer-route")
      .log("Headers : ${headers}")
      .log("Body : ${body}");

    }
}
