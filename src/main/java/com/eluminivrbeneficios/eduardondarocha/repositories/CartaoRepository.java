package com.eluminivrbeneficios.eduardondarocha.repositories;

import com.eluminivrbeneficios.eduardondarocha.entities.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
}
