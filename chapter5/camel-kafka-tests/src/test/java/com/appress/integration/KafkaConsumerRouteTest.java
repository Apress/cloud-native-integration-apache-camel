package com.appress.integration;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;

@QuarkusTest
public class KafkaConsumerRouteTest {

    @Inject
    CamelContext camelContext;

    @Inject
    ProducerTemplate producerTemplate;

    @ConfigProperty(name = "kafka.uri.from")
    String direct;

    @ConfigProperty(name = "final.endpoint")
    String mock;

    private static final String MSG = "Hello";


    @Test
    public void test() throws InterruptedException {

        producerTemplate.sendBody(direct, MSG);

        MockEndpoint mockEndpoint = camelContext.getEndpoint(mock, MockEndpoint.class);
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.assertIsSatisfied();

        assertEquals(MSG,mockEndpoint.getExchanges().get(0).getMessage().getBody());

    }

}
