package com.apress.integration;

import org.apache.camel.builder.RouteBuilder;

public class RestChoiceRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        rest("/RestChoice")
        .get()
        .id("rest-choice")
        .produces("text/plain")
        .route()
        .choice()
            .when(header("preferred_title").isEqualToIgnoreCase("mrs"))
                .setBody(simple("Hi Mrs. ${header.name}"))
            .when(header("preferred_title").isEqualToIgnoreCase("mr"))
                .setBody(simple("Hi Mr. ${header.name}"))
            .when(header("preferred_title").isEqualToIgnoreCase("ms"))
                .setBody(simple("Hi Ms. ${header.name}"))
            .otherwise()
                .setBody(simple("Hey ${header.name}"));
    }


}
