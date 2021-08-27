package com.apress.integration.route;

import org.apache.camel.builder.RouteBuilder;

public class HealthCheckRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

      rest("/healthApp")
      .get()
          .route()
          .routeId("health-route")
          .log("Request Received")
          .setBody(constant("{{application.message}}"))
      .endRest();

    }
}
