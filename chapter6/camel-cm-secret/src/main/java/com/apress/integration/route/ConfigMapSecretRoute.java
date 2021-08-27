package com.apress.integration.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class ConfigMapSecretRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

      rest("/ConfigMapSecret")
      .get()
          .route()
          .routeId("cm-secret")
          .log("Request Received")
          .choice()
              .when(header("password").isEqualTo(constant("{{password}}" )))
                 .setBody(constant("{{application.message}}"))
                 .log("Authorized")
              .otherwise()
              .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(403))
              .log("Not Authorized")
      .endRest();

    }
}
