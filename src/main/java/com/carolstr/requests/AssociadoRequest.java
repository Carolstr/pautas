package com.carolstr.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoRequest {

    @NotNull(message = "Campo nome obrigatório!")
    @NotEmpty(message = "Campo nome obrigatório!")
    private String nome;

    @NotNull(message = "Campo cpf obrigatório!")
    @NotEmpty(message = "Campo cpf obrigatório!")
    private String cpf;

    private String cep;
}
