package com.eluminivrbeneficios.eduardondarocha.exceptions;

public class CartaoJaExistenteException extends RuntimeException{
    public CartaoJaExistenteException (String numCartao){
        super("Cartao com número " + numCartao + " já foi criado");
    }
}
