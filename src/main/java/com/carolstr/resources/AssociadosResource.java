package com.carolstr.resources;

import com.carolstr.requests.AssociadoRequest;
import com.carolstr.services.AssociadosService;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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




}