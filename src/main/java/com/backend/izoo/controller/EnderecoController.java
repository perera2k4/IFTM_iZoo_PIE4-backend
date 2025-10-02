package com.backend.izoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.izoo.dto.EnderecoDTO;
import com.backend.izoo.service.EnderecoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/endereco")
@Validated
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> buscarTodos() {
        List<EnderecoDTO> lista = enderecoService.buscarTodos();
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EnderecoDTO> buscarPorId(@PathVariable String id) {
        try {
            EnderecoDTO endereco = enderecoService.buscarPorId(id);
            return ResponseEntity.ok().body(endereco);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> inserir(@Valid @RequestBody EnderecoDTO endereco) {
        try {
            EnderecoDTO entidade = enderecoService.inserir(endereco);
            return ResponseEntity.status(HttpStatus.CREATED).body(entidade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EnderecoDTO> atualizar(@PathVariable String id, @Valid @RequestBody EnderecoDTO endereco) {
        try {
            EnderecoDTO enderecoAtualizado = enderecoService.atualizar(id, endereco);
            return ResponseEntity.ok().body(enderecoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<EnderecoDTO> atualizarParcial(@PathVariable String id, @RequestBody EnderecoDTO endereco) {
        try {
            EnderecoDTO enderecoAtualizado = enderecoService.atualizarParcial(id, endereco);
            return ResponseEntity.ok().body(enderecoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        try {
            enderecoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoints adicionais para busca por critérios específicos

    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<List<EnderecoDTO>> buscarPorCidade(@PathVariable String cidade) {
        List<EnderecoDTO> lista = enderecoService.buscarPorCidade(cidade);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EnderecoDTO>> buscarPorEstado(@PathVariable String estado) {
        List<EnderecoDTO> lista = enderecoService.buscarPorEstado(estado);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EnderecoDTO>> buscarPorCidadeEEstado(
            @RequestParam String cidade, 
            @RequestParam String estado) {
        List<EnderecoDTO> lista = enderecoService.buscarPorCidadeEEstado(cidade, estado);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/com-localizacao")
    public ResponseEntity<List<EnderecoDTO>> buscarComLocalizacao() {
        List<EnderecoDTO> lista = enderecoService.buscarComLocalizacao();
        return ResponseEntity.ok().body(lista);
    }
}
