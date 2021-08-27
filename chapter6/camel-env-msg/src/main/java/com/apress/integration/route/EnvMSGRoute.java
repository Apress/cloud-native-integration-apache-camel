package com.apress.integration.route;

import org.apache.camel.builder.RouteBuilder;

public class EnvMSGRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

      rest("/helloEnv")
      .get()
          .route()
          .routeId("env-msg")
          .log("Request Received")
          .setBody(constant("{{app.msg}}"))
      .endRest();

    }
}
