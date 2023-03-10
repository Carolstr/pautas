package com.carolstr.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarPautaRequest {

    private String nome;
    private String descricao;
    private LocalDateTime dataExpiracao;
}
