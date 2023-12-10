package com.eluminivrbeneficios.eduardondarocha.exceptions.service;

public class SenhaInvalidaException extends RuntimeException{
    public SenhaInvalidaException(){
        super("Senha inválida!");
    }
}
