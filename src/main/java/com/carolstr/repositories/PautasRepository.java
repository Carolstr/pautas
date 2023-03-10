package com.carolstr.repositories;

import com.carolstr.entities.Pauta;
import com.carolstr.entities.PautaStatus;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class PautasRepository implements PanacheMongoRepository<Pauta> {

    public List<Pauta> buscarPautasCadastradas(PautaStatus status, String nome) {
        StringBuilder query = new StringBuilder();
        query.append("{");

        if (Objects.nonNull(status) && Objects.nonNull(nome)) {
            query.append("'status': '").append(status).append("'").append(",'nome': /").append(nome).append("/i");
        }else if (Objects.nonNull(status)) {
            query.append("'status': '").append(status).append("'");
        }else if (Objects.nonNull(nome)) {
            query.append("'nome':  /").append(nome).append("/i");
        }

        query.append("}");
        return find(query.toString()).list();
    }
}
