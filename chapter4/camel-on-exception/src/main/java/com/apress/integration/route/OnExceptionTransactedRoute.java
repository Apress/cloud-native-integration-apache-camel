package com.apress.integration.route;

import com.apress.integration.entity.Fruit;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import java.util.List;
import static com.apress.integration.constant.HTTPConstant.*;

public class OnExceptionTransactedRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        rest("/onExceptionTransacted")
            .bindingMode(RestBindingMode.json)
            .post()
                .type(Fruit.class)
                .outType(String.class)
                .consumes(APP_JSON)
                .produces(TEXT_PLAIN)
                .route()
                    .routeId("save-fruit-route")
                    .onException(Exception.class)
                        .handled(true)
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                        .setHeader(Exchange.CONTENT_TYPE, constant(TEXT_PLAIN))
                        .setBody(exceptionMessage())
                        .markRollbackOnly()
                    .end()
                    .transacted()
                    .to("jpa:" + Fruit.class.getName())
                    .choice()
                    .when(simple("${body.name} == 'apple' "))
                        .throwException( new Exception("I don't like this fruit"))
                    .otherwise()
                        .setHeader(Exchange.CONTENT_TYPE, constant(TEXT_PLAIN))
                        .setBody(constant("I like this fruit!"))
                .endRest()
            .get()
                .outType(List.class)
                .produces(APP_JSON)
                .route()
                    .routeId("list-fruits")
                    .to("jpa:"+Fruit.class.getName()+"?query={{query.all}}" );

    }
}
