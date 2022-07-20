package com.ronilsonalves.testes.attornatus.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice(basePackages = "com.ronilsonalves.testes.attornatus.api.controllers")
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(PessoaNotFoundException.class)
    public ResponseEntity<MessageExceptionHandler> pessoaNotFound(PessoaNotFoundException pessoaNotFoundException) {
        MessageExceptionHandler error = new MessageExceptionHandler(
                LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), pessoaNotFoundException.getMessage()
        );
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(EnderecoPositionInvalidException.class)
    public ResponseEntity<MessageExceptionHandler> invalidPositionException(EnderecoPositionInvalidException enderecoPositionInvalidException) {
        MessageExceptionHandler error = new MessageExceptionHandler(
                LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), enderecoPositionInvalidException.getMessage()
        );
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}
