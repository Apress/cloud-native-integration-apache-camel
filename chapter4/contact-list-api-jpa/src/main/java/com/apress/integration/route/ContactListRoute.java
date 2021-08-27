package com.apress.integration.route;

import com.apress.integration.entity.Contact;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import java.util.List;

public class ContactListRoute extends RouteBuilder {

    public static final String MEDIA_TYPE_APP_JSON = "application/json";

    @Override
    public void configure() throws Exception {

        rest("/contact")
            .bindingMode(RestBindingMode.json)
            .post()
                .type(Contact.class)
                .outType(Contact.class)
                .consumes(MEDIA_TYPE_APP_JSON)
                .produces(MEDIA_TYPE_APP_JSON)
                .route()
                    .routeId("save-contact-route")
                    .log("saving contacts")
                    .to("jpa:" + Contact.class.getName())
                .endRest()
            .get()
                .outType(List.class)
                .produces(MEDIA_TYPE_APP_JSON)
                .route()
                    .routeId("list-contact-route")
                    .log("listing contacts")
                    .to("jpa:" + Contact.class.getName()+ "?query={{query}}")
                .endRest();
      
    }
}
