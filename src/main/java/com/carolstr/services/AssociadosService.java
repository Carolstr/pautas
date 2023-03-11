package com.carolstr.services;


import com.carolstr.client.apiCep.ApiCepClient;
import com.carolstr.client.apiCep.responses.ApiCepResponse;
import com.carolstr.entities.Associado;
import com.carolstr.entities.Pauta;
import com.carolstr.entities.PautaStatus;
import com.carolstr.repositories.AssociadosRepository;
import com.carolstr.requests.AssociadoRequest;
import com.carolstr.responses.AssociadoResponse;
import com.carolstr.responses.AssociadosResponse;
import com.carolstr.responses.PautaResponse;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public AssociadosResponse buscarAssociadosCadastrados(){
        List<AssociadoResponse> associados =  repository.findAll().list().stream().map(associado -> {
            return AssociadoResponse.builder()
                    .id(associado.getId().toString())
                    .nome(associado.getNome())
                    .cpf(associado.getCpf())
                    .cidade(associado.getCidade())
                    .uf(associado.getUf())
                    .votoValido(associado.isVotoValido())
                    .build();
        }).sorted(Comparator.comparing(AssociadoResponse::getNome)).collect(Collectors.toList());

        return AssociadosResponse.builder().associados(associados).build();

    }

    public void deletarAssociado(String id) throws Exception {
        Associado associado = repository.findByIdOptional(new ObjectId(id)).orElseThrow(() ->
                new Exception("Ops... Associado inválido!"));

        repository.delete(associado);
    }



}
