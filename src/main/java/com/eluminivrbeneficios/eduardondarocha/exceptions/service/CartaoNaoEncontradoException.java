package com.eluminivrbeneficios.eduardondarocha.exceptions.service;

public class CartaoNaoEncontradoException extends RuntimeException{
    public CartaoNaoEncontradoException(String numCartao){
        super("Cartão com número " + numCartao + " não encontrado");
    }
}
