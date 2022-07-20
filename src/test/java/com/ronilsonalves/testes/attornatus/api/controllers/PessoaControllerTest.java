package com.ronilsonalves.testes.attornatus.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ronilsonalves.testes.attornatus.api.services.PessoaService;
import com.ronilsonalves.testes.attornatus.domain.dtos.EnderecoDTO;
import com.ronilsonalves.testes.attornatus.domain.dtos.PessoaDTO;
import com.ronilsonalves.testes.attornatus.domain.models.Endereco;
import com.ronilsonalves.testes.attornatus.domain.models.Pessoa;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PessoaControllerTest {

    @MockBean
    private PessoaService pessoaService;

    @Autowired
    private MockMvc mockMvc;

    private String urlTemplate = "/pessoas/";

    Pessoa pessoaParaSalvar;
    Pessoa segundaPessoaParaSalvar;
    Pessoa pessoaParaAtualizar;
    PessoaDTO pessoaParaSalvarDTO;
    PessoaDTO pessoaParaAtualizarDTO;
    EnderecoDTO enderecoParaSalvarDTO;
    Endereco enderecoParaSalvar;
    Endereco segundoEnderecoParaSalvar;
    Endereco enderecoParaAtualizar;
    String requestBodyParaSalvar;
    String segundaRequestBodyParaSalvar;
    String requestBodyParaAtualizar;
    String requestBodyParaSalvarEnd;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {

        //objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        //objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());

        List<Endereco> enderecos = new ArrayList<>();
        List<Endereco> enderecosPessoaParaSalvar = new ArrayList<>();
        pessoaParaSalvarDTO = new PessoaDTO();
        pessoaParaAtualizarDTO = new PessoaDTO();
        enderecoParaSalvarDTO = new EnderecoDTO();
        enderecoParaSalvar = new Endereco(1L,"Rua dos Patos", "12345-000",10,"País das Maravilhas");
        segundoEnderecoParaSalvar = new Endereco(2L,"Rua das Gaivotas", "32154-999",100,"Wakanda");
        enderecoParaAtualizar = new Endereco(null,"Rua das Gaivotas", "32154-099",127,"Wakanda");
        pessoaParaSalvar = new Pessoa(1L,"Ciclano da Silva Mendes", LocalDate.now(),enderecosPessoaParaSalvar,null);
        pessoaParaSalvar.getEnderecos().add(enderecoParaSalvar);
        pessoaParaSalvar.getEnderecos().add(segundoEnderecoParaSalvar);
        pessoaParaSalvar.setEnderecoPrincipal(enderecoParaSalvar);
        segundaPessoaParaSalvar = new Pessoa(2L,"Fulano", LocalDate.now(),enderecos,null);
        segundaPessoaParaSalvar.getEnderecos().add(enderecoParaSalvar);
        segundaPessoaParaSalvar.getEnderecos().add(segundoEnderecoParaSalvar);
        pessoaParaAtualizar = new Pessoa(1L,"Ciclano", LocalDate.now(),enderecos,null);
        pessoaParaAtualizar.getEnderecos().add(enderecoParaSalvar);
        pessoaParaAtualizar.getEnderecos().add(enderecoParaAtualizar);

        BeanUtils.copyProperties(pessoaParaSalvar, pessoaParaSalvarDTO);
        BeanUtils.copyProperties(pessoaParaAtualizar, pessoaParaAtualizarDTO);
        BeanUtils.copyProperties(enderecosPessoaParaSalvar, enderecoParaSalvarDTO);

        try {
            requestBodyParaSalvar = objectMapper.writeValueAsString(pessoaParaSalvar);
            segundaRequestBodyParaSalvar = objectMapper.writeValueAsString(segundaPessoaParaSalvar);
            requestBodyParaAtualizar = objectMapper.writeValueAsString(pessoaParaAtualizar);
            requestBodyParaSalvarEnd = objectMapper.writeValueAsString(enderecoParaSalvar);
        } catch (JsonProcessingException e) {
            System.Logger.Level.INFO.getName();
        }


    }

    @Test
    void deveListarPessoas() throws Exception {
        doReturn(Lists.newArrayList(pessoaParaSalvar,segundaPessoaParaSalvar)).when(pessoaService).listarPessoas();

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string(HttpHeaders.LOCATION,nullValue()))
                .andExpect(jsonPath("$[*]", hasSize(2)));

    }

    @Test
    void deveConsultarPessoaPeloId() throws Exception {
        doReturn(pessoaParaSalvar).when(pessoaService).consultarPessoaPeloId(1L);

        mockMvc.perform(get(urlTemplate+"1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string(HttpHeaders.LOCATION, nullValue()))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void deveCriarPessoa() throws Exception {
        doReturn(pessoaParaSalvar).when(pessoaService).salvarPessoa(pessoaParaSalvarDTO);

        mockMvc.perform(post("/pessoas").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(requestBodyParaSalvar))
                .andExpect(status().isCreated());
    }

    @Test
    void deveEditarPessoa() throws Exception{
        doReturn(pessoaParaAtualizar).when(pessoaService).editarPessoa(1L, pessoaParaAtualizarDTO);
        mockMvc.perform(put("/pessoas/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(requestBodyParaAtualizar))
                .andExpect(status().isOk());
    }

    @Test
    void deveCriarEnderecoParaPessoa() throws Exception {
        doReturn(pessoaParaAtualizar).when(pessoaService).criarEnderecoParaPessoa(1L,enderecoParaSalvarDTO);
        mockMvc.perform(put(urlTemplate+"/2/enderecos/criarEndereco").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(requestBodyParaSalvarEnd))
                .andExpect(status().isCreated());
    }

    @Test
    void deveDefinirEnderecoPrincipal() throws Exception {
        doReturn(pessoaParaSalvar).when(pessoaService).definirEnderecoPrincipal(1L,1);

        mockMvc.perform(put(urlTemplate+"1/enderecos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(header().string(HttpHeaders.LOCATION, nullValue()))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", is("Endereço principal atualizado com sucesso!")));
    }

    @Test
    void deveListarEnderecosPorPessoaId() throws Exception {
        doReturn(Lists.newArrayList(pessoaParaSalvar.getEnderecos())).when(pessoaService).listarEnderecosPorPessoaId(1L);

        mockMvc.perform(get(urlTemplate+"1/enderecos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string(HttpHeaders.LOCATION,nullValue()))
                .andExpect(jsonPath("$[*]", hasSize(2)));
    }

}