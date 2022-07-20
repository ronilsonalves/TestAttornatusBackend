package com.ronilsonalves.testes.attornatus.domain.dtos;

import com.ronilsonalves.testes.attornatus.domain.models.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PessoaDTO implements Serializable {

    private Long id;
    private String nome;
    private LocalDate dataDeNascimento;
    private List<Endereco> enderecos;
    private Endereco enderecoPrincipal;
}
