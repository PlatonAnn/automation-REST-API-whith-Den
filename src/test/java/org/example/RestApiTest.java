package org.example;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestApiTest {

    private static String token;

    @BeforeAll
    public static void shouldAuthenticatedWithLogin() {
        token = given()
                .baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .contentType(ContentType.JSON)
                .when()
                .body("{\n" +
                        "  \"password\": \"u7ljdajLNo7PsVw7\",\n" +
                        "  \"rememberMe\": true,\n" +
                        "  \"username\": \"admin\"\n" +
                        "}")
                .post("/authenticate").
                        then().statusCode(HttpStatus.SC_OK)
                .extract().path("id_token");
    }

    @Test
    public void shouldGetAuthorities() {
        given()
                .baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(token)
                .when()
                .get("/authorities").
                then().statusCode(HttpStatus.SC_OK);
    }
}

