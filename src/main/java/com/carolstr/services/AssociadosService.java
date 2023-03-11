package com.carolstr.services;


import com.carolstr.client.apiCpf.ApiCepClient;
import com.carolstr.client.apiCpf.responses.ApiCepResponse;
import com.carolstr.entities.Associado;
import com.carolstr.repositories.AssociadosRepository;
import com.carolstr.requests.AssociadoRequest;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class AssociadosService {

    @Inject
    AssociadosRepository repository;

    @RestClient
    ApiCepClient apiCepClient;

    public Associado cadastrarAssociado(AssociadoRequest request) throws Exception {
        Optional<Associado> optionalAssociado = repository.buscarAssociadoCpf(request.getCpf());
        if(optionalAssociado.isPresent()){
            throw new Exception("Ops... associado já cadastrado!");
        }

        Associado associado = new Associado();
        associado.setNome(request.getNome());
        associado.setCpf(request.getCpf());
        associado.setVotoValido(true);

        if(Objects.nonNull(request.getCep())){
            try {
                ApiCepResponse response = apiCepClient.buscarCEP(request.getCep());
                associado.setCidade(response.getLocalidade());
                associado.setUf(response.getUf());
            }catch (Exception e){
                throw new Exception("Ops... CEP inválido!");
            }
        }

        repository.persist(associado);

        return associado;
    }




}
