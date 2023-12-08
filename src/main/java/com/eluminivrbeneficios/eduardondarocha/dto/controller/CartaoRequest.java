package com.eluminivrbeneficios.eduardondarocha.dto.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoRequest {
    private String numeroCartao;
    private String senha;
}
