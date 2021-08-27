package com.apress.integration.route;

import org.apache.camel.builder.RouteBuilder;

public class HelloMinikubeRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

      rest("/helloMinikube")
      .get()
          .route()
          .routeId("k8s-hello-minikube")
          .log("Request Received.")
          .setBody(constant("Hello from minikube."))
      .endRest();

    }
}
