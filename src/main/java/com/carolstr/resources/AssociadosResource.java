package com.carolstr.resources;

import com.carolstr.entities.Associado;
import com.carolstr.exception.PautaInvalidaException;
import com.carolstr.requests.AssociadoRequest;
import com.carolstr.responses.AssociadosResponse;
import com.carolstr.responses.PautaResponse;
import com.carolstr.responses.PautasResponse;
import com.carolstr.services.AssociadosService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

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
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Associado cadastrado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Associado.class))),
            @APIResponse(responseCode = "404", description = "Associado invalido")
    })
    public Response cadastrarAssociado(AssociadoRequest request) throws PautaInvalidaException {
        return Response.status(Response.Status.CREATED).entity(service.cadastrarAssociado(request)).build();
    }

    @GET
    @Operation(summary = "Buscar associados cadastrados no sistema")
    @APIResponse(responseCode = "200", description = "sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = AssociadosResponse.class)))
    public Response buscarAssociados(){
        return Response.status(Response.Status.OK).entity(service.buscarAssociadosCadastrados()).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir associado")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "sucesso"),
            @APIResponse(responseCode = "404", description = "Associado invalido")
    })
    public Response deletarAssociados(@PathParam("id") String id) throws PautaInvalidaException {
        service.deletarAssociado(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}