package com.appress.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RestKafkaRouteTest {

    @Test
    public void test()  {

        given()
            .contentType(ContentType.TEXT)
            .body("Hello")
        .when()
            .post("/kafka")
        .then()
            .statusCode(200)
            .body(is("message sent."));

    }
}
