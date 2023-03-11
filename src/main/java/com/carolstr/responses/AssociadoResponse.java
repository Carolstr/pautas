package com.carolstr.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssociadoResponse {

    private String id;
    private String nome;
    private String cpf;
    private String cidade;
    private String uf;
    private boolean votoValido;


}
