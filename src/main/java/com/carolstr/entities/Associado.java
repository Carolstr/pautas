package com.carolstr.entities;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "associados")
public class Associado {

    public ObjectId id;
    private String nome;
    private String cpf;
    private String cidade;
    private String uf;
    private boolean votoValido;


}
