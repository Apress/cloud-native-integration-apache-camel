package com.apress.integration.route;

import com.apress.integration.exception.DontLikeException;
import com.apress.integration.entity.Fruit;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import static com.apress.integration.constant.HTTPConstant.APP_JSON;
import static com.apress.integration.constant.HTTPConstant.TEXT_PLAIN;

public class OnExceptionContinuedRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
        .continued(true)
        .log(LoggingLevel.ERROR, "Exception Handled")
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
        .setBody(exceptionMessage());


        rest("/onExceptionContinued")
            .bindingMode(RestBindingMode.json)
            .post()
                .type(Fruit.class)
                .outType(String.class)
                .consumes(APP_JSON)
                .produces(TEXT_PLAIN)
                .route()
                    .routeId("continued-route")
                    .choice()
                    .when(simple("${body.name} == 'apple' "))
                        .throwException(new DontLikeException("Try another Fruit."))
                    .otherwise()
                        .setBody(constant("I like this fruit."))
                    .end()
                    .setHeader(Exchange.CONTENT_TYPE, constant(TEXT_PLAIN) )
                    .log("Gets Executed.")
                .endRest();

    }
}
