package com.ronilsonalves.testes.attornatus.api.services;

import com.ronilsonalves.testes.attornatus.domain.dtos.EnderecoDTO;
import com.ronilsonalves.testes.attornatus.domain.dtos.PessoaDTO;
import com.ronilsonalves.testes.attornatus.domain.models.Endereco;
import com.ronilsonalves.testes.attornatus.domain.models.Pessoa;
import com.ronilsonalves.testes.attornatus.domain.repositories.PessoaRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    Pessoa pessoaParaSalvar;
    Pessoa segundaPessoaParaSalvar;
    Pessoa pessoaParaAtualizar;
    PessoaDTO pessoaParaSalvarDTO;
    PessoaDTO pessoaParaAtualizarDTO;
    EnderecoDTO enderecoParaSalvarDTO;
    Endereco enderecoParaSalvar;
    Endereco segundoEnderecoParaSalvar;
    Endereco enderecoParaAtualizar;

    @BeforeEach
    void setUp() {
        List<Endereco> enderecos = new ArrayList<>();
        List<Endereco> enderecosPessoaParaSalvar = new ArrayList<>();
        pessoaParaSalvarDTO = new PessoaDTO();
        pessoaParaAtualizarDTO = new PessoaDTO();
        enderecoParaSalvarDTO = new EnderecoDTO();
        enderecoParaSalvar = new Endereco(1L,"Rua dos Patos", "12345-000",10,"País das Maravilhas");
        segundoEnderecoParaSalvar = new Endereco(2L,"Rua das Gaivotas", "32154-999",100,"Wakanda");
        enderecoParaAtualizar = new Endereco(34L,"Rua das Gaivotas", "32154-099",127,"Wakanda");
        pessoaParaSalvar = new Pessoa(1L,"Ciclano da Silva Mendes", LocalDate.now(),enderecosPessoaParaSalvar,null);
        pessoaParaSalvar.getEnderecos().add(enderecoParaSalvar);
        pessoaParaSalvar.getEnderecos().add(segundoEnderecoParaSalvar);
        pessoaParaSalvar.setEnderecoPrincipal(enderecoParaSalvar);
        segundaPessoaParaSalvar = new Pessoa(2L,"Fulano", LocalDate.now(),enderecos,enderecoParaSalvar);
        segundaPessoaParaSalvar.getEnderecos().add(enderecoParaSalvar);
        segundaPessoaParaSalvar.getEnderecos().add(segundoEnderecoParaSalvar);
        pessoaParaAtualizar = new Pessoa(null,"Ciclano", LocalDate.now(),enderecos,null);
        pessoaParaAtualizar.getEnderecos().add(enderecoParaSalvar);
        pessoaParaAtualizar.getEnderecos().add(enderecoParaAtualizar);
        pessoaParaAtualizar.setEnderecoPrincipal(enderecoParaAtualizar);

        BeanUtils.copyProperties(pessoaParaSalvar, pessoaParaSalvarDTO);
        BeanUtils.copyProperties(pessoaParaAtualizar, pessoaParaAtualizarDTO);
        BeanUtils.copyProperties(enderecosPessoaParaSalvar, enderecoParaSalvarDTO);
    }

    @Test
    void listarPessoas() {

        List<Pessoa> pessoasList = pessoaService.listarPessoas();
        Assertions.assertEquals(2,pessoasList.size(),"Buscar pessoas retornou 2 pessoas");
    }

    @Test
    void salvarPessoa() {
        //doReturn(pessoaParaSalvar).when(pessoaRepository).save(any(Pessoa.class));
        Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoaParaSalvarDTO);

        Assertions.assertNotNull(pessoaSalva);
    }

    @Test
    void consultarPessoaPeloId() {
        Optional<Pessoa> pessoaPeloId = Optional.ofNullable(pessoaService.consultarPessoaPeloId(1L));

        Assertions.assertNotNull(pessoaPeloId);
    }

    @Test
    void editarPessoa() {
        pessoaParaSalvarDTO.setNome("Ciclano");
        Pessoa pessoaAtualizada = pessoaService.editarPessoa(1L,pessoaParaSalvarDTO);
        System.out.println(pessoaAtualizada);
        Assertions.assertTrue(pessoaAtualizada.getNome().equalsIgnoreCase("Ciclano"));
    }

    @Test
    void criarEnderecoParaPessoa() {
        Pessoa pessoaComNovoEndereco = pessoaService.criarEnderecoParaPessoa(1L,new EnderecoDTO(39L,"Rua das Flores","00012-345",123,"Cidade Jardins"));
        System.out.println(pessoaComNovoEndereco.getEnderecos().size());
        Assertions.assertTrue(pessoaComNovoEndereco.getEnderecos().size() == 3);
    }

    @Test
    void definirEnderecoPrincipal() {
        Pessoa pessoaAtualizada = pessoaService.definirEnderecoPrincipal(1L,1);

        Assertions.assertNotNull(pessoaAtualizada.getEnderecoPrincipal());
    }

    @Test
    void listarEnderecosPorPessoaId() {
        //when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaParaSalvar);
        //Optional<Pessoa> pessoaPeloId = Optional.ofNullable(pessoaService.consultarPessoaPeloId(1L));
        List<Endereco> enderecos = pessoaService.listarEnderecosPorPessoaId(1L);

        Assertions.assertEquals(2, enderecos.size(),"Lista de endereços da pessoa obtida com sucesso!");
    }
}