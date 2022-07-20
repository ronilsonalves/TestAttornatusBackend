package com.ronilsonalves.testes.attornatus.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class MessageExceptionHandler {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;
}
