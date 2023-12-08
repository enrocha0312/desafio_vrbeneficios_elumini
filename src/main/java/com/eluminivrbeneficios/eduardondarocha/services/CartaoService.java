package com.eluminivrbeneficios.eduardondarocha.services;

import com.eluminivrbeneficios.eduardondarocha.entities.Cartao;
import com.eluminivrbeneficios.eduardondarocha.repositories.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoService {
    @Autowired
    private CartaoRepository cartaoRepository;

    public Cartao criar (Cartao cartao){
        cartao.setId(null);
        cartao.setSaldo(0.0);
        return cartaoRepository.save(cartao);
    }

    public Double retornarSaldoDeCartaoPorNumero(String numCartao){
        Optional<Cartao> cartao = cartaoRepository.findByNumeroCartao(numCartao);
        return cartao.get().getSaldo();
    }
}
