package com.eluminivrbeneficios.eduardondarocha.services;

import com.eluminivrbeneficios.eduardondarocha.dto.service.Transacao;
import com.eluminivrbeneficios.eduardondarocha.entities.Cartao;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoJaExistenteException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoNaoEncontradoException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.SaldoInsuficienteException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.SenhaInvalidaException;
import com.eluminivrbeneficios.eduardondarocha.repositories.CartaoRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Cartao atualizarCartao (String numCartao, Cartao cartao){
        try{
            cartao.setNumeroCartao(numCartao);
            cartao = cartaoRepository.save(cartao);
            return cartao;
        }catch (EntityNotFoundException e){
            throw new CartaoNaoEncontradoException(numCartao);
        }
    }

    public Double retornarSaldoDeCartaoPorNumero(String numCartao){
        Optional<Cartao> optionalCartao = cartaoRepository.findById(numCartao);
        optionalCartao.orElseThrow(() -> new CartaoNaoEncontradoException(numCartao));
        return optionalCartao.get().getSaldo();
    }

    public String realizarTransacao(Transacao transacao){
        Optional<Cartao> optionalCartao = cartaoRepository.findById(transacao.getNumeroCartao());
        optionalCartao.orElseThrow(() -> new CartaoNaoEncontradoException(transacao.getNumeroCartao()));
        optionalCartao
                .stream()
                .filter(c -> c.getSenha().equals(transacao.getNumeroCartao()))
                .findFirst()
                .orElseThrow(()-> new SenhaInvalidaException());
        optionalCartao
                .stream()
                .filter(c -> (c.getSaldo() - transacao.getValor())> 0.0)
                .findFirst()
                .orElseThrow(()-> new SaldoInsuficienteException());
        optionalCartao.get().setSaldo(optionalCartao.get().getSaldo()-transacao.getValor());
        cartaoRepository.save(optionalCartao.get());
        return "Ok";
    }
}
