package com.eluminivrbeneficios.eduardondarocha.dto.controller;

import java.io.Serializable;

public class TransacaoRequest implements Serializable {
    private String numeroCartao;
    private String senha;
    private Double valor;
}
