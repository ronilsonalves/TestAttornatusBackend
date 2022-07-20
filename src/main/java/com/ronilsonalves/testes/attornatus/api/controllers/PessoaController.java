package com.ronilsonalves.testes.attornatus.api.controllers;

import com.ronilsonalves.testes.attornatus.api.services.PessoaService;
import com.ronilsonalves.testes.attornatus.domain.dtos.EnderecoDTO;
import com.ronilsonalves.testes.attornatus.domain.dtos.PessoaDTO;
import com.ronilsonalves.testes.attornatus.domain.models.Pessoa;
import com.ronilsonalves.testes.attornatus.exceptionhandler.EnderecoPositionInvalidException;
import com.ronilsonalves.testes.attornatus.exceptionhandler.PessoaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarPessoas(){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.listarPessoas());
    }

    @GetMapping("/{pessoaId}")
    public ResponseEntity<?> consultarPessoaPeloId(@PathVariable Long pessoaId) throws PessoaNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.consultarPessoaPeloId(pessoaId));
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> criarPessoa(@RequestBody PessoaDTO pessoaParaSalvar) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.salvarPessoa(pessoaParaSalvar));
    }

    @PutMapping("/{pessoaId}")
    public ResponseEntity<?> editarPessoa(@PathVariable Long pessoaId, @RequestBody PessoaDTO pessoaParaEditar) throws PessoaNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.editarPessoa(pessoaId, pessoaParaEditar));
    }

    @PutMapping("/{pessoaId}/enderecos/criarEndereco")
    public ResponseEntity<?> criarEnderecoParaPessoa(@PathVariable Long pessoaId, @RequestBody EnderecoDTO enderecoParaPessoa) throws PessoaNotFoundException{
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.criarEnderecoParaPessoa(pessoaId,enderecoParaPessoa));
    }

    @PutMapping("/{pessoaId}/enderecos/{posicaoEndereco}")
    public ResponseEntity<?> definirEnderecoPrincipal(@PathVariable Long pessoaId, @PathVariable Integer posicaoEndereco) throws PessoaNotFoundException, EnderecoPositionInvalidException {
        pessoaService.definirEnderecoPrincipal(pessoaId,(posicaoEndereco-1));
        return ResponseEntity.status(HttpStatus.OK).body("Endereço principal atualizado com sucesso!");
    }

    @GetMapping("/{pessoaId}/enderecos")
    public ResponseEntity<?> listarEnderecosPorPessoaId(@PathVariable Long pessoaId) throws PessoaNotFoundException {
            return ResponseEntity.status(HttpStatus.OK).body(pessoaService.listarEnderecosPorPessoaId(pessoaId));
    }
}
