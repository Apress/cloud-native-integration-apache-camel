package com.appress.integration.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class KafkaConsumerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

      from("{{kafka.uri}}")
      .routeId("consumer-route")
      .log("Headers : ${headers}")
      .log("Body : ${body}")
      .process(new Processor() {
          @Override
          public void process(Exchange exchange) throws Exception {
              log.info("My thread is : " + Thread.currentThread().getName());
              log.info("Going to sleep...");
              Thread.sleep(10000);
          }
      });

    }
}
