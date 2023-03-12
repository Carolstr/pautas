package com.carolstr.resources;

import com.carolstr.entities.Pauta;
import com.carolstr.entities.PautaStatus;
import com.carolstr.exception.PautaInvalidaException;
import com.carolstr.requests.AtualizarPautaRequest;
import com.carolstr.requests.PautaRequest;
import com.carolstr.responses.PautaResponse;
import com.carolstr.responses.PautasResponse;
import com.carolstr.services.PautasService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pautas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PautasResource {

    @Inject
    PautasService service;

    @POST
    @Operation(summary = "Criar pauta")
    @APIResponse(responseCode = "201", description = "Pauta criada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = Pauta.class)))
    public Response criarPauta(@Valid PautaRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.criarPauta(request)).build();
    }

    @GET
    @Operation(summary = "Buscar pautas cadastradas no sistema")
    @APIResponse(responseCode = "200", description = "sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = PautasResponse.class)))
    public Response buscarPautasCadastradas(@QueryParam("status") PautaStatus status, @QueryParam("nome") String nome){
        return Response.ok(service.listarPautasCadastradas(status, nome)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar detalhes da pauta por id")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PautaResponse.class))),
            @APIResponse(responseCode = "404", description = "Pauta invalida")
    })
    public Response detalhesPauta(@PathParam("id") String id) throws PautaInvalidaException {
        return Response.ok(service.buscarDetalhesPauta(id)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "deletar pauta por id")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "sucesso"),
            @APIResponse(responseCode = "404", description = "Pauta invalida")
    })
    public Response deletarPauta(@PathParam("id") String id) throws PautaInvalidaException {
        service.deletarPauta(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/{id}")
    @Operation(summary = "atualizar pauta por id")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "sucesso"),
            @APIResponse(responseCode = "404", description = "Pauta invalida")
    })
    public Response atualizarPauta(@PathParam("id") String id, @Valid AtualizarPautaRequest request) throws PautaInvalidaException {
        service.atualizarPauta(id, request);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/votar-pauta/{id}/{cpf}")
    @Operation(summary = "atualizar pauta por id")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "sucesso"),
            @APIResponse(responseCode = "404", description = "Pauta invalida")
    })
    public Response votarPauta(@PathParam("id") String id, @PathParam("cpf") String cpf,
                                   @QueryParam("voto") boolean voto) throws PautaInvalidaException {
        service.votarPauta(id, cpf, voto);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}