package com.apress.integration.health;

import org.apache.camel.CamelContext;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import org.jboss.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;

@Readiness
@Singleton
public class AppReadiness implements HealthCheck {

    private static final Logger LOG = Logger.getLogger(AppReadiness.class);

    @Inject
    CamelContext context;

    @Override
    public HealthCheckResponse call() {

        LOG.trace("Testing Readiness");

        HealthCheckResponseBuilder builder;

        if(context.isStarted()){
            builder = HealthCheckResponse.named("Camel UP").up();
        }else{
            builder = HealthCheckResponse.named("Camel DOWN").down();
        }

        return builder.build();

    }
}
