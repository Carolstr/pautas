package com.carolstr.resources;

import com.carolstr.repositories.AssociadosRepository;
import com.carolstr.requests.AssociadoRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(AssociadosResource.class)
class AssociadosResourceTest {

    @Inject
    AssociadosRepository associadosRepository;

    @Order(1)
    @Test
    public void cadastrarAssociadoSucesso(){
        AssociadoRequest request = new AssociadoRequest();
        request.setNome("teste");
        request.setCpf("42061290027");

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
    public void cadastrarAssociadoJaCadastrado(){
        AssociadoRequest request = new AssociadoRequest();
        request.setNome("teste");
        request.setCpf("42061290027");

        given()
                .contentType(ContentType.JSON)
                .body(request)
        .when()
                .post()
        .then()
                .statusCode(404)
                .body("mensagem", is("Ops... associado já cadastrado!"));

    }

    @Order(3)
    @Test
    public void buscarAssociadosSucesso(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(200);

    }

    @Order(4)
    @Test
    public void deletarAssociadoSucesso(){
        String id = associadosRepository.buscarAssociadoCpf("42061290027").get().getId().toString();
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
        .when()
                .delete("/{id}")
        .then()
                .statusCode(204);

    }


}