package com.carolstr.client.apiCpf.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiCepResponse {

    private String uf;
    private String localidade;
}
