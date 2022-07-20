package com.ronilsonalves.testes.attornatus.exceptionhandler;

public class EnderecoPositionInvalidException extends IndexOutOfBoundsException {

    public EnderecoPositionInvalidException(String message) {
        super(message);
    }
}
