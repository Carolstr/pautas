package com.carolstr.resources;

import com.carolstr.requests.AssociadoRequest;
import com.carolstr.services.AssociadosService;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pautas/associados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AssociadosResource {

    @Inject
    AssociadosService service;

    @POST
    @Operation(summary = "Cadastrar associado")
    public Response cadastrarAssociado(AssociadoRequest request) throws Exception {
        return Response.status(Response.Status.CREATED).entity(service.cadastrarAssociado(request)).build();
    }

    @GET
    @Operation(summary = "Buscar associados cadastrados no sistema")
    public Response buscarAssociados(){
        return Response.status(Response.Status.OK).entity(service.buscarAssociadosCadastrados()).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir associado")
    public Response deletarAssociados(@PathParam("id") String id) throws Exception {
        service.deletarAssociado(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}