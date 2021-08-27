package com.apress.integration.bean;

import io.quarkus.arc.Unremovable;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.jboss.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;
import com.apress.integration.entity.Contact;

@Singleton
@Unremovable
@Named("contactsBean")
public class ContactsBean {

    private Set<Contact> contacts = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    private static final Logger LOG = Logger.getLogger(ContactsBean.class);

    public ContactsBean() {
    }

    @PostConstruct
    public void init(){
        contacts.add(new Contact("Bill","bill@email.com","99999999"));
        contacts.add(new Contact("Joe", "joe@email.com","00000000"));
    }


    public void  listContacts(Message message) {
        message.setBody(contacts);
    }


    public void addContact(Message message) {
        if( message.getBody() != null){
            contacts.add(message.getBody(Contact.class)) ;
        }else{
            LOG.info("Empty body");
        }
    }

    public void checkExchange(Exchange exchange){
        for (String component: exchange.getContext().getComponentNames()){
            LOG.info("Component: " + component);
        }
    }




}
