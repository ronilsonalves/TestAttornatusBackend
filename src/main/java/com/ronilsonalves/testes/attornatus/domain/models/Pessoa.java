package com.ronilsonalves.testes.attornatus.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "pessoas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataDeNascimento;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Endereco> enderecos;

    @OneToOne
    @JoinColumn(name = "enderecos_id")
    private Endereco enderecoPrincipal;
}
