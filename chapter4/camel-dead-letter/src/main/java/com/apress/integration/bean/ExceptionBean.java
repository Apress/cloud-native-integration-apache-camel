package com.apress.integration.bean;

import io.quarkus.arc.Unremovable;
import org.apache.camel.Message;
import org.jboss.logging.Logger;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
@Named("exceptionBean")
@Unremovable
public class ExceptionBean {

    private static final Logger LOG = Logger.getLogger(ExceptionBean.class);

    int counter;

    public void analyze(Message message) throws Exception{

        ++counter;

        LOG.info("Attempt Number " + counter);

        message.setBody(UUID.randomUUID().toString());

        if(counter < 3){
            throw new Exception("Not Now!");
        }

    }


}
