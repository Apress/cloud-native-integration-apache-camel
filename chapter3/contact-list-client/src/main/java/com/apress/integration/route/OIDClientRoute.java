package com.apress.integration.route;

import org.apache.camel.builder.RouteBuilder;

public class OIDClientRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:OIDC-client-timer?period=3000")
            .routeId("OIDC-client-route")
            .bean("tokenHandlerBean")
            .to("vertx-http:http://localhost:8080/contact")
            .log("${body}");

    }
}
