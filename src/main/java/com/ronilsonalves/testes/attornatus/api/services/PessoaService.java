package com.ronilsonalves.testes.attornatus.api.services;

import com.ronilsonalves.testes.attornatus.domain.dtos.EnderecoDTO;
import com.ronilsonalves.testes.attornatus.domain.dtos.PessoaDTO;
import com.ronilsonalves.testes.attornatus.domain.models.Endereco;
import com.ronilsonalves.testes.attornatus.domain.models.Pessoa;
import com.ronilsonalves.testes.attornatus.domain.repositories.EnderecoRepository;
import com.ronilsonalves.testes.attornatus.domain.repositories.PessoaRepository;
import com.ronilsonalves.testes.attornatus.exceptionhandler.EnderecoPositionInvalidException;
import com.ronilsonalves.testes.attornatus.exceptionhandler.PessoaNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;

    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public Pessoa salvarPessoa(PessoaDTO pessoaParaSalvar) {
        Pessoa pessoaSalva = new Pessoa();
        BeanUtils.copyProperties(pessoaParaSalvar,pessoaSalva);
        return pessoaRepository.save(pessoaSalva);
    }

    public Pessoa consultarPessoaPeloId(Long pessoaId) {
        return pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new PessoaNotFoundException(
                        "Não foi possível obter uma pessoa com o identificador informado!"));
    }


    @Transactional(propagation=Propagation.REQUIRED)
    public Pessoa editarPessoa(Long pessoaId, PessoaDTO pessoaParaEditar) {
        Pessoa pessoaEditada = new Pessoa();
        BeanUtils.copyProperties(pessoaParaEditar, pessoaEditada);
        if(pessoaRepository.findById(pessoaId).isPresent()){
            Pessoa pessoaParaVerificar = pessoaRepository.findById(pessoaId).get();
            Endereco enderecoPrincipal = verificarSeEnderecoPricipalFoiRemovido(pessoaParaVerificar.getEnderecoPrincipal(), pessoaEditada.getEnderecos());
            pessoaEditada.setEnderecoPrincipal(enderecoPrincipal);
            return pessoaRepository.save(pessoaEditada);
        } else {
            throw new PessoaNotFoundException("Não foi possível atualizar as informações da pessoa " +
                    "desejada, favor verificar o identificador informado.");
        }
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public Pessoa criarEnderecoParaPessoa(Long pessoaId, EnderecoDTO enderecoParaPessoa) {
        Pessoa pessoaParaEditar = consultarPessoaPeloId(pessoaId);
        Endereco novoEndereco = new Endereco();
        BeanUtils.copyProperties(enderecoParaPessoa, novoEndereco);
        pessoaParaEditar.getEnderecos().add(novoEndereco);
        return pessoaRepository.save(pessoaParaEditar);
    }

    public Endereco verificarSeEnderecoPricipalFoiRemovido(Endereco enderecoPrincipal, List<Endereco> enderecosNovos) {
        List<Endereco> enderecosComId = enderecosNovos.stream().filter(endereco -> endereco.getId() == enderecoPrincipal.getId()).collect(Collectors.toList());

        if(enderecosComId.isEmpty()){
            return null;
        }
        return enderecoPrincipal;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public Pessoa definirEnderecoPrincipal(Long pessoaId, Integer posicaoEndereco) throws IndexOutOfBoundsException {
        Pessoa pessoaParaAtualizar = consultarPessoaPeloId(pessoaId);
        try {
            pessoaParaAtualizar.setEnderecoPrincipal(pessoaParaAtualizar.getEnderecos().get(posicaoEndereco));
            return pessoaRepository.save(pessoaParaAtualizar);
        } catch (IndexOutOfBoundsException e) {
            throw new EnderecoPositionInvalidException("O endereço selecionado não existe! Certifique-se de que está passando" +
                    " uma posição válida, ou se a pessoa não possuir um endereço, utilize a API para realizar o cadastro de um" +
                    " antes de tentar definir um endereço principal.");
        }
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public List<Endereco> listarEnderecosPorPessoaId(Long pessoaId) {
        if (pessoaRepository.findById(pessoaId).isPresent()) {
            return pessoaRepository.findById(pessoaId).get().getEnderecos();
        } else {
            throw new PessoaNotFoundException("Não foi possível encontrar os endereços da" +
                    " pessoa informada, favor verificar se ela existe ou se o identificador está correto.");
        }
    }
}
