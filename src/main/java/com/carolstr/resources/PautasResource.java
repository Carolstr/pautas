package com.carolstr.resources;

import com.carolstr.entities.PautaStatus;
import com.carolstr.requests.PautaRequest;
import com.carolstr.services.PautasService;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.inject.Inject;
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
    public Response criarPauta(PautaRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.criarPauta(request)).build();
    }

    @GET
    @Operation(summary = "Buscar pautas cadastradas no sistema")
    public Response buscarPautasCadastradas(@QueryParam("status") PautaStatus status, @QueryParam("nome") String nome){
        return Response.ok(service.listarPautasCadastradas(status, nome)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar detalhes da pauta por id")
    public Response detalhesPauta(@PathParam("id") String id) throws Exception {
        return Response.ok(service.buscarDetalhesPauta(id)).build();
    }
    

}