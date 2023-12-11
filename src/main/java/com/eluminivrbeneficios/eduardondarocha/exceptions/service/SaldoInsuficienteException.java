package com.eluminivrbeneficios.eduardondarocha.exceptions.service;

public class SaldoInsuficienteException extends RuntimeException{
    public SaldoInsuficienteException(){
        super("Saldo insuficiente!");
    }
}
