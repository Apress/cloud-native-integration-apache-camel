package com.apress.integration.route;

import com.apress.integration.entity.Contact;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import java.util.List;
import static com.apress.integration.constant.HTTPConstant.APP_JSON;

public class ContactListRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        rest("/contact")
            .bindingMode(RestBindingMode.json)
            .post()
                .type(Contact.class)
                .outType(Contact.class)
                .consumes(APP_JSON)
                .produces(APP_JSON)
                .route()
                    .routeId("save-contact-route")
                    .transacted()
                    .log("saving contacts")
                    .to("jpa:" + Contact.class.getName())
                    .log("Pausing the Transaction")
                    .process(new Processor() {
                        @Override
                        public void process(Exchange exchange) throws Exception {
                            Thread.sleep(7000);
                        }
                    })
                    .log("Transaction Finished")
                .endRest()
            .get()
                .outType(List.class)
                .produces(APP_JSON)
                .route()
                    .routeId("list-contact-route")
                    .to("jpa:" + Contact.class.getName()+ "?query={{query.all}}");
    }
}
