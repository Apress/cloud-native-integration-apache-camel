package com.apress.integration.route;

import com.apress.integration.entity.Fruit;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import static com.apress.integration.constant.HTTPConstant.*;

public class TryCatchRoute extends RouteBuilder {



    @Override
    public void configure() throws Exception {
        rest("/tryCatch")
            .bindingMode(RestBindingMode.json)
            .post()
                .type(Fruit.class)
                .outType(String.class)
                .consumes(APP_JSON)
                .produces(TEXT_PLAIN)
                .route()
                    .routeId("taste-fruit-route")
                    .doTry()
                        .choice()
                        .when(simple("${body.name} == 'apple' "))
                            .throwException( new Exception("I don't like this fruit"))
                        .otherwise()
                            .setBody(constant("I like this fruit!"))
                    .endDoTry()
                    .doCatch(Exception.class)
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                        .setBody(exceptionMessage())
                    .doFinally()
                        .setHeader(Exchange.CONTENT_TYPE, constant(TEXT_PLAIN))
                        .log("Exchange processing completed!")
                .endRest();

    }
}
