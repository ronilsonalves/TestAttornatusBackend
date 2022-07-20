package com.ronilsonalves.testes.attornatus.domain.dtos;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoDTO implements Serializable {
    private Long id;
    private String logradouro;
    private String cep;
    private Integer numero;
    private String cidade;
}
