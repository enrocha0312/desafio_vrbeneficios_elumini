package com.eluminivrbeneficios.eduardondarocha.exceptions.service;

public class CartaoJaExistenteException extends RuntimeException{
    public CartaoJaExistenteException (String numCartao){
        super("Cartão com número " + numCartao + " já foi criado");
    }
}
