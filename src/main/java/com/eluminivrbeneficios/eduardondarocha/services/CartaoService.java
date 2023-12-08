package com.eluminivrbeneficios.eduardondarocha.services;

import com.eluminivrbeneficios.eduardondarocha.entities.Cartao;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoJaExistenteException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoNaoEncontradoException;
import com.eluminivrbeneficios.eduardondarocha.repositories.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoService {
    @Autowired
    private CartaoRepository cartaoRepository;

    public Cartao criar (Cartao cartao){
        cartao.setSaldo(0.0);
        Optional<Cartao> optionalCartao = cartaoRepository.findById(cartao.getNumeroCartao());
        optionalCartao.ifPresent(c -> {
            throw new CartaoJaExistenteException(c.getNumeroCartao());
        });
        return cartaoRepository.save(cartao);
    }

    public Double retornarSaldoDeCartaoPorNumero(String numCartao){
        Optional<Cartao> optionalCartao = cartaoRepository.findById(numCartao);
        optionalCartao.orElseThrow(() -> new CartaoNaoEncontradoException(numCartao));
        return optionalCartao.get().getSaldo();
    }

}
