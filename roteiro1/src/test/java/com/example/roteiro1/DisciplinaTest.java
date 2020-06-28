package com.example.roteiro1;


import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class DisciplinaTest {
    private String url;

    @Before
    public void setUp() {
        this.url = "http://localhost:8080/v1/api/disciplinas";

        JSONObject payload = new JSONObject();
        payload.put("nome", "Portugues");
        payload.put("nota", 9.0);

        Response response = given()
                .headers("Content-Type", "application/json")
                .body(payload.toJSONString())
                .post(url);
    }

    @Test
    public void testGet() {
        given()
                .when()
                .get(url)
                .then()
                .body("nome", hasItem("Portugues"));
    }

    @Test
    public void testPost() {
        JSONObject body = new JSONObject();
        body.put("nome", "Historia");
        body.put("nota", 9.5);

        Response response = given()
                .contentType("application/json")
                .body(body.toJSONString())
                .post(url);

        response.then().assertThat().statusCode(200);
    }

    @Test
    public void testGetDisciplina() {
        String urlGet = url + "/" + 2;
        given()
                .when()
                .get(urlGet)
                .then()
                .body("nome", equalTo("Historia"));
    }

    @Test
    public void testPutName() {
        String urlPutName = url + "/" + 3 + "/nome";
        JSONObject body = new JSONObject();
        body.put("nome", "Matematica");

        Response response = given()
                .contentType("application/json")
                .body(body.toJSONString())
                .patch(urlPutName);

        response
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testPutNota() {
        String urlPutNota = url + "/" + 3 + "/nota";
        JSONObject body = new JSONObject();
        body.put("nota", 8.8);

        Response response = given()
                .contentType("application/json")
                .body(body.toJSONString())
                .patch(urlPutNota);

        response
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testDelete() {
        int delete = 1;
        String urlDelete = url + "/" + delete;


        Response response = given()
                .delete(urlDelete);

        response.then()
                .assertThat()
                .statusCode(200);
    }
}
