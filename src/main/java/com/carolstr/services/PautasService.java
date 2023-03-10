package com.carolstr.services;


import com.carolstr.entities.Pauta;
import com.carolstr.entities.PautaStatus;
import com.carolstr.repositories.PautasRepository;
import com.carolstr.requests.PautaRequest;
import com.carolstr.responses.PautaResponse;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PautasService {

    @Inject
    PautasRepository repository;

    public Pauta criarPauta(PautaRequest request){

        Pauta pauta = new Pauta();
        pauta.setNome(request.getNome());
        pauta.setDescricao(request.getDescricao());
        pauta.setParticipantes(new ArrayList<>());
        pauta.setVotosPositivos(0);
        pauta.setVotosNegativos(0);
        pauta.setStatus(PautaStatus.ATIVA);
        pauta.setDataCriacao(LocalDateTime.now());

        if(request.getDataExpiracao() != null){
            pauta.setDataExpiracao(request.getDataExpiracao());
        }else {
            pauta.setDataExpiracao(LocalDateTime.now().plusMinutes(1));
        }

        repository.persist(pauta);

        return pauta;
    }


    public List<PautaResponse> listarPautasCadastradas(PautaStatus status, String nome){
       return repository.buscarPautasCadastradas(status, nome).stream().map(pauta -> {
           return PautaResponse.builder()
                   .id(pauta.getId().toString())
                   .nome(pauta.getNome())
                   .descricao(pauta.getDescricao())
                   .status(pauta.getStatus())
                   .dataCriacao(pauta.getDataCriacao())
                   .build();
        }).sorted(Comparator.comparing(PautaResponse::getNome)).collect(Collectors.toList());

    }

    public PautaResponse buscarDetalhesPauta(String id) throws Exception {
        Optional<Pauta> pauta = repository.findByIdOptional(new ObjectId(id));

        if(pauta.isPresent()){
            return PautaResponse.builder()
                    .id(pauta.get().getId().toString())
                    .nome(pauta.get().getNome())
                    .descricao(pauta.get().getDescricao())
                    .dataExpíracao(pauta.get().getDataExpiracao())
                    .dataCriacao(pauta.get().getDataCriacao())
                    .status(pauta.get().getStatus())
                    .votosPositivos(pauta.get().getVotosPositivos())
                    .votosNegativos(pauta.get().getVotosNegativos())
                    .build();
        }else{
            throw new Exception("Ops... Pauta não encontrada!");
        }
    }


}
