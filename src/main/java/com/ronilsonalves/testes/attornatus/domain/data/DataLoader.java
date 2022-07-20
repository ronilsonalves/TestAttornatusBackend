package com.ronilsonalves.testes.attornatus.domain.data;

import com.ronilsonalves.testes.attornatus.domain.models.Endereco;
import com.ronilsonalves.testes.attornatus.domain.models.Pessoa;
import com.ronilsonalves.testes.attornatus.domain.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final PessoaRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(new Endereco(null,"Avenida Almirante Maximiano Fonseca","96204-040",16,"Rio Grande"));
        enderecos.add(new Endereco(null,"Rua Serra de Bragança","03318-000",19,"São Paulo"));
        repository.save(new Pessoa(null, "Ciclano da Cunha", LocalDate.now(), enderecos, null));
        enderecos.clear();
        enderecos.add(new Endereco(null,"Rua dos Carijós","30120-060",18,"Belo Horizonte"));
        enderecos.add(new Endereco(null,"Avenida Esbertalina Barbosa Damiani","29946-490",21,"São Mateus"));
        repository.save(new Pessoa(null, "Fulano da Silva", LocalDate.now(), enderecos,enderecos.get(0)));
    }
}
