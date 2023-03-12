package com.carolstr.resources;

import com.carolstr.repositories.PautasRepository;
import com.carolstr.requests.AtualizarPautaRequest;
import com.carolstr.requests.PautaRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(PautasResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PautasResourceTest {

    @Inject
    PautasRepository pautasRepository;

    @Order(1)
    @Test
    public void criarPautaSucesso(){
        PautaRequest request = new PautaRequest();
        request.setNome("teste");
        request.setDescricao("descricao teste");

        given()
                .contentType(ContentType.JSON)
        .body(request)
                .when()
        .post()
                .then()
                .statusCode(201);

    }

    @Order(2)
    @Test
    public void buscarPautasSucesso(){
         given()
                .contentType(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(200);

    }

    @Order(3)
    @Test
    public void buscarPautaDetalheSucesso(){
        String id = pautasRepository.find("nome= 'teste'").firstResult().getId().toString();
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
        .when()
                .get("/{id}")
        .then()
                .statusCode(200);

    }

    @Order(4)
    @Test
    public void atualizarPautaSucesso(){
        String id = pautasRepository.find("{'nome':'teste'}").firstResult().getId().toString();

        AtualizarPautaRequest request = new AtualizarPautaRequest();
        request.setNome("teste");
        request.setDescricao("nova descricao");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .pathParam("id", id)
        .when()
                .patch("/{id}")
        .then()
                .statusCode(204);

    }
}