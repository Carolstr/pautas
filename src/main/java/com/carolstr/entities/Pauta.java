package com.carolstr.entities;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "pautas")
public class Pauta {

    public ObjectId id;
    private String nome;
    private String descricao;
    private LocalDateTime dataExpiracao;
    private Integer votosPositivos;
    private Integer votosNegativos;
    private List<String> participantes;
    private PautaStatus status;
}
