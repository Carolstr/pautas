package com.carolstr.repositories;

import com.carolstr.entities.Associado;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class AssociadosRepository implements PanacheMongoRepository<Associado> {

    public Optional<Associado> buscarAssociadoCpf(String cpf){
        return find("{'cpf': ?1}", cpf).firstResultOptional();
    }
}
