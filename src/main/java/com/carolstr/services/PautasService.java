package com.carolstr.services;


import com.carolstr.entities.Associado;
import com.carolstr.entities.Pauta;
import com.carolstr.entities.PautaStatus;
import com.carolstr.repositories.AssociadosRepository;
import com.carolstr.repositories.PautasRepository;
import com.carolstr.requests.AtualizarPautaRequest;
import com.carolstr.requests.PautaRequest;
import com.carolstr.responses.PautaResponse;
import io.quarkus.scheduler.Scheduled;
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

    @Inject
    AssociadosRepository associadosRepository;

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
                    .dataExpiracao(pauta.get().getDataExpiracao())
                    .dataCriacao(pauta.get().getDataCriacao())
                    .status(pauta.get().getStatus())
                    .votosPositivos(pauta.get().getVotosPositivos())
                    .votosNegativos(pauta.get().getVotosNegativos())
                    .build();
        }else{
            throw new Exception("Ops... Pauta não encontrada!");
        }
    }

    public void deletarPauta(String id) throws Exception {
        Optional<Pauta> pauta = repository.findByIdOptional(new ObjectId(id));

        if(pauta.isPresent() && !pauta.get().getStatus().equals(PautaStatus.ENCERRADA)){
            repository.delete(pauta.get());
        }else{
            throw new Exception("Ops... Não foi possível excluir a pauta!");
        }
    }

    public void atualizarPauta(String id, AtualizarPautaRequest request) throws Exception {
        Pauta pauta = repository.findByIdOptional(new ObjectId(id)).orElseThrow(() ->
                new Exception("Ops... Pauta não encontrada!"));

        if(pauta.getVotosPositivos() > 0 || pauta.getVotosNegativos() > 0){
            throw new Exception("Ops... Não é possível alterar uma pauta que ja foi votada!");
        }

        pauta.setNome(request.getNome());
        pauta.setDescricao(request.getDescricao());
        pauta.setDataExpiracao(request.getDataExpiracao());

        repository.update(pauta);
    }

    public void votarPauta(String pautaId, String cpf, boolean voto) throws Exception {
        Pauta pauta = repository.findByIdOptional(new ObjectId(pautaId)).orElseThrow(() ->
                new Exception("Ops... pauta inválida!"));

        if(!pauta.getStatus().equals(PautaStatus.ATIVA)) throw new Exception("Ops... a pauta não esta mais ativa!");

        Associado associado = associadosRepository.buscarAssociadoCpf(cpf).orElseThrow(() ->
                new Exception("Ops... cpf não cadastrado!"));

        if(!associado.isVotoValido()) throw new Exception("Ops... voto indisponível para o cpf!");

        if(pauta.getParticipantes().stream().anyMatch(participante -> participante.equalsIgnoreCase(associado.id.toString()))){
            throw new Exception("Ops... você ja votou para esta pauta!");
        }

        if(voto){
            pauta.setVotosPositivos(pauta.getVotosPositivos() + 1);
        }else{
            pauta.setVotosNegativos(pauta.getVotosNegativos() + 1);
        }

        pauta.adicionarParticipante(associado.id.toString());

        repository.update(pauta);
    }

    @Scheduled(every = "60s")
    public void expirarPautas(){
        List<Pauta> pautas = repository.findAll().list();
        for(Pauta pauta: pautas){
            if(pauta.getDataExpiracao().isBefore(LocalDateTime.now())){
                if(pauta.getVotosPositivos() == pauta.getVotosNegativos()) pauta.setStatus(PautaStatus.ENCERRADA);
                else if(pauta.getVotosPositivos() > pauta.getVotosNegativos()) pauta.setStatus(PautaStatus.APROVADA);
                else if(pauta.getVotosPositivos() < pauta.getVotosNegativos()) pauta.setStatus(PautaStatus.RECUSADA);
            }

            repository.update(pauta);
        }
    }


}
