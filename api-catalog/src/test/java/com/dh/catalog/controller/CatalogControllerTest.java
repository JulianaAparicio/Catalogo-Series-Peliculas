package com.dh.catalog.controller;

import com.dh.catalog.models.Movie;
import com.dh.catalog.models.Serie;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;

class CatalogControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void create() throws InterruptedException {
        var responseMovie= given()
                .contentType(ContentType.JSON)
                .when()
                .body(new Movie(null,"movieTest","cienciaFiccion","urlStreamTest"))
                .post("/api/v1/movies/save")
                .as(Map.class);

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre",responseMovie.get("genre"))
                .get("/api/v1/catalog/online/{genre}")
                .then()
                .statusCode(200)
                //.body("name", equalTo("movieTest"));
                .body(("random.object"), CoreMatchers.equalTo(arrayWithSize(2)));

        var responseSerie= given()
                .contentType(ContentType.JSON)
                .accept("application/json, text/plain, */*")
                .when()
                .body(new Serie(UUID.randomUUID().toString(),"serieTest","cienciaFiccion"))
                .post("/api/v1/series");

        given()
                .contentType(ContentType.JSON)
                .accept("application/json, text/plain, */*")
                .when()
                .pathParam("genre",responseMovie.get("genre"))
                .get("/api/v1/catalog/online/{genre}")
                .then()
                .statusCode(200)
                .body("id", equalTo(responseSerie));

        //TimeUnit.SECONDS.sleep(10);

//        given()
//                .contentType(ContentType.JSON)
//                .when()
//                .pathParam("genre",responseMovie.get("genre"))
//                .get("/api/v1/catalog/offline/{genre}")
//                .then()
//                .statusCode(200)
//                .body("name", equalTo("movieTest"));
//
//        given()
//                .contentType(ContentType.JSON)
//                .accept("application/json, text/plain, */*")
//                .when()
//                .pathParam("genre",responseMovie.get("genre"))
//                .get("/api/v1/catalog/offline/{genre}")
//                .then()
//                .statusCode(200)
//                .body("id", equalTo(responseSerie));


    }
}