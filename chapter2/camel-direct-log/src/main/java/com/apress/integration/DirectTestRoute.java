package com.apress.integration;

import org.apache.camel.builder.RouteBuilder;

public class DirectTestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
      rest("/directTest")
          .post("/path1")
              .id("path1")
              .route()
                .to("log:path1-logger")
                .to("direct:logger")
                .setBody(constant("path1"))
              .endRest()
          .post("/path2")
              .id("path2")
              .route()
                  .to("log:path2-logger")
                  .to("direct:logger")
                  .setBody(constant("path2"))
              .endRest();

       from("direct:logger")
         .routeId("logger-route")
         .to("log:logger-route?showAll=true");
    }








}
