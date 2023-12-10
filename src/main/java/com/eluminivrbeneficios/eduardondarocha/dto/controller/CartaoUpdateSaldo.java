package com.eluminivrbeneficios.eduardondarocha.dto.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartaoUpdateSaldo {
    private String numeroCartao;
    private String senha;
    private Double saldo;
}
