package com.ronilsonalves.testes.attornatus.domain.repositories;

import com.ronilsonalves.testes.attornatus.domain.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}