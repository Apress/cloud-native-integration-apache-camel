package com.apress.integration.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import com.apress.integration.entity.Contact;

public class ContactListRoute extends RouteBuilder {

    public static final String MEDIA_TYPE_APP_JSON = "application/json";

    @Override
    public void configure() throws Exception {

        rest("/contact")
            .bindingMode(RestBindingMode.json)
            .post()
            .consumes(MEDIA_TYPE_APP_JSON)
            .produces(MEDIA_TYPE_APP_JSON)
            .type(Contact.class)
                .route()
                    .routeId("save-contact-route")
                    .log("saving contacts")
                    .bean("contactsBean", "addContact")
                .endRest()
            .get()
                .produces(MEDIA_TYPE_APP_JSON)
                .route()
                    .routeId("list-contact-route")
                    .log("listing contacts")
                    .bean("contactsBean", "listContacts")
                .endRest();

    }
}
