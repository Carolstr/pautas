package com.carolstr.client.apiCpf;

import com.carolstr.client.apiCpf.responses.ApiCepResponse;
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

