package com.carolstr.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PautaRequest {

    @NotNull(message = "Campo nome obrigatório!")
    @NotEmpty(message = "Campo nome obrigatório!")
    private String nome;
    private String descricao;
    private LocalDateTime dataExpiracao;
}
