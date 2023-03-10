package com.carolstr.responses;

import com.carolstr.entities.PautaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PautaResponse {

    public String id;
    public String nome;
    public String descricao;
    public LocalDateTime dataExpíracao;
    public LocalDateTime dataCriacao;
    public PautaStatus status;
    public Integer votosPositivos;
    public Integer votosNegativos;

}
