package com.carolstr.client.apiCep;

import com.carolstr.client.apiCep.responses.ApiCepResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RegisterRestClient(configKey = "api-cpf")
@Path("/ws")
public interface ApiCepClient {

    @GET
    @Path("/{cep}/json")
    ApiCepResponse buscarCEP(@PathParam("cep") String cep);
 
}

