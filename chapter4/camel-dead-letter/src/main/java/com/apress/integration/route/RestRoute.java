package com.apress.integration.route;

import org.apache.camel.builder.RouteBuilder;
import static com.apress.integration.constant.HTTPConstant.*;

public class RestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        rest("/deadLetter")
        .consumes(TEXT_PLAIN)
        .produces(TEXT_PLAIN)
        .post()
             .route()
                 .routeId("rest-route")
                 .log("Redirecting message")
                 .wireTap("seda:process-route")
                 .setBody(constant("Thanks!"))
        .endRest();

    }
}
