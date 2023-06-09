package com.carolstr.responses;

import com.carolstr.entities.PautaStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PautaResponse {

    private String id;
    private String nome;
    private String descricao;
    private LocalDateTime dataExpiracao;
    private LocalDateTime dataCriacao;
    private PautaStatus status;
    private Integer votosPositivos;
    private Integer votosNegativos;

}
