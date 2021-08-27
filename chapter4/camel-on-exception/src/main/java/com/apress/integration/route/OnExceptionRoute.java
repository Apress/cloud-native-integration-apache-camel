package com.apress.integration.route;

import com.apress.integration.exception.DontLikeException;
import com.apress.integration.entity.Fruit;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import static com.apress.integration.constant.HTTPConstant.*;

public class OnExceptionRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
        .handled(true)
        .log(LoggingLevel.ERROR, "Exception Handled")
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
        .setBody(exceptionMessage());

        onException(DontLikeException.class)
        .handled(true)
        .log(LoggingLevel.ERROR,"DontLikeException Handled")
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
        .setBody(constant("There is a problem with apples, try another fruit."));

        rest("/onException")
            .bindingMode(RestBindingMode.json)
            .post()
                .type(Fruit.class)
                .outType(String.class)
                .consumes(APP_JSON)
                .produces(TEXT_PLAIN)
                .route()
                    .routeId("taste-fruit-route")
                    .choice()
                    .when(simple("${body.name} == 'apple' "))
                        .throwException(new DontLikeException())
                    .otherwise()
                        .throwException(new Exception("Another Problem."))
                    .end()
                    .log("never get executed.")
                .endRest();

    }
}
