package com.eluminivrbeneficios.eduardondarocha.dto.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transacao implements Serializable {
    private String numeroCartao;
    private String senha;
    private Double valor;
}
