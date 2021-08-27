package com.apress.integration.route;

import com.apress.integration.entity.Contact;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactListRoute extends RouteBuilder {

    public static final String MEDIA_TYPE_APP_JSON = "application/json";

    @Override
    public void configure() throws Exception {

        declareInterface();
        declareGetListContactRoute();
        declareSearchContactByIdRoute();
        declareSaveContactRoute();


    }

    private void declareInterface(){
        rest("/contact")
        .bindingMode(RestBindingMode.json)
        .post()
            .type(Contact.class)
            .outType(Contact.class)
            .consumes(MEDIA_TYPE_APP_JSON)
            .produces(MEDIA_TYPE_APP_JSON)
            .to("direct:create-contact")
        .get()
            .outType(List.class)
            .produces(MEDIA_TYPE_APP_JSON)
            .to("direct:list-contacts")
        .get("/{id}")
            .outType(Contact.class)
            .produces(MEDIA_TYPE_APP_JSON)
            .to("direct:search-by-id");
    }

    private void declareSaveContactRoute(){
      from("direct:create-contact")
          .routeId("save-contact-route")
          .log("saving contacts")
          .to("jpa:" + Contact.class.getName());
    }

    /*

    In this example we are injecting the parameters using CDI, but we could also pass it as a header.
    Here is what you would need to add to the route:

          .process(new Processor() {
                  @Override
                  public void process(Exchange exchange) throws Exception {
                      Map<String, Object> parameters = new HashMap<>();
                      parameters.put("company", "${header.company}" );
                      exchange.getMessage().setHeader(JpaConstants.JPA_PARAMETERS_HEADER, parameters);
                  }
              })

     */
    private void declareGetListContactRoute(){
       from("direct:list-contacts")
           .routeId("list-contact-route")
           .choice()
           .when(simple("${header.company} != null"))
            .log("Listing contacts by company = ${header.company}")
            .to("jpa:" + Contact.class.getName()+ "?query={{query.company}}&parameters=#mapParameters")
           .otherwise()
            .to("jpa:" + Contact.class.getName()+ "?query={{query.all}}");
    }

    private void declareSearchContactByIdRoute(){
        from("direct:search-by-id")
            .routeId("search-by-id-route")
            .log("Searching Contact by id = ${header.id}")
            .setBody(header("id").convertTo(Integer.class))
            .to("jpa:" + Contact.class.getName()+ "?findEntity=true");
    }

    @Produces()
    @Named("mapParameters")
    public Map createMapParameters(){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("company", "${header.company}" );
        return  parameters;
    }

}
