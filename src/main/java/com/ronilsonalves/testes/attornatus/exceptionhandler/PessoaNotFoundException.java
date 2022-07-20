package com.ronilsonalves.testes.attornatus.exceptionhandler;

public class PessoaNotFoundException extends RuntimeException{

    public PessoaNotFoundException(String message) {
        super(message);
    }
}
