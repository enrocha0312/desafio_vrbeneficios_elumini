package com.eluminivrbeneficios.eduardondarocha.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cartoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cartao {
    @Id
    private String numeroCartao;
    @NotEmpty
    private String senha;
    @NotNull
    private Double saldo;
}
