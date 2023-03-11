package com.carolstr.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoRequest {

    private String nome;
    private String cpf;
    private String cep;
}
