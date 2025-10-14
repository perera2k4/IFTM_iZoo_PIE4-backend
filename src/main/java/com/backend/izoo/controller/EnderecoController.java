package com.backend.izoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/endereco")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
@Tag(name = "Endereços", description = "Endpoints para gerenciamento de endereços")
@SecurityRequirement(name = "Bearer Authentication")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    @Operation(
        summary = "Listar todos os endereços",
        description = "Retorna uma lista com todos os endereços cadastrados. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<EnderecoDTO>> buscarTodos() {
        List<EnderecoDTO> lista = enderecoService.buscarTodos();
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value = "/{id}")
    @Operation(
        summary = "Buscar endereço por ID",
        description = "Retorna os dados de um endereço específico. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<EnderecoDTO> buscarPorId(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable String id) {
        try {
            EnderecoDTO endereco = enderecoService.buscarPorId(id);
            return ResponseEntity.ok().body(endereco);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Criar novo endereço",
        description = "Cadastra um novo endereço no sistema. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<EnderecoDTO> inserir(
            @Parameter(description = "Dados do endereço a ser criado", required = true)
            @Valid @RequestBody EnderecoDTO endereco) {
        try {
            EnderecoDTO entidade = enderecoService.inserir(endereco);
            return ResponseEntity.status(HttpStatus.CREATED).body(entidade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}")
    @Operation(
        summary = "Atualizar endereço completo",
        description = "Atualiza todos os dados de um endereço. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<EnderecoDTO> atualizar(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable String id, 
            @Parameter(description = "Dados atualizados do endereço", required = true)
            @Valid @RequestBody EnderecoDTO endereco) {
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
    @Operation(
        summary = "Atualizar endereço parcial",
        description = "Atualiza parcialmente os dados de um endereço. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<EnderecoDTO> atualizarParcial(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable String id, 
            @Parameter(description = "Dados parciais para atualização", required = true)
            @RequestBody EnderecoDTO endereco) {
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
    @Operation(
        summary = "Deletar endereço",
        description = "Remove um endereço do sistema. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do endereço a ser deletado", required = true)
            @PathVariable String id) {
        try {
            enderecoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoints adicionais para busca por critérios específicos

    @GetMapping("/cidade/{cidade}")
    @Operation(
        summary = "Buscar endereços por cidade",
        description = "Retorna uma lista de endereços filtrados por cidade. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de endereços da cidade",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<EnderecoDTO>> buscarPorCidade(
            @Parameter(description = "Nome da cidade", required = true)
            @PathVariable String cidade) {
        List<EnderecoDTO> lista = enderecoService.buscarPorCidade(cidade);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/estado/{estado}")
    @Operation(
        summary = "Buscar endereços por estado",
        description = "Retorna uma lista de endereços filtrados por estado (UF). Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de endereços do estado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<EnderecoDTO>> buscarPorEstado(
            @Parameter(description = "Sigla do estado (UF)", required = true, example = "MG")
            @PathVariable String estado) {
        List<EnderecoDTO> lista = enderecoService.buscarPorEstado(estado);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/buscar")
    @Operation(
        summary = "Buscar endereços por cidade e estado",
        description = "Retorna uma lista de endereços filtrados por cidade e estado. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de endereços filtrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<EnderecoDTO>> buscarPorCidadeEEstado(
            @Parameter(description = "Nome da cidade", required = true)
            @RequestParam String cidade, 
            @Parameter(description = "Sigla do estado (UF)", required = true, example = "MG")
            @RequestParam String estado) {
        List<EnderecoDTO> lista = enderecoService.buscarPorCidadeEEstado(cidade, estado);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/com-localizacao")
    @Operation(
        summary = "Buscar endereços com coordenadas",
        description = "Retorna uma lista de endereços que possuem latitude e longitude definidas. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de endereços com localização",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<EnderecoDTO>> buscarComLocalizacao() {
        List<EnderecoDTO> lista = enderecoService.buscarComLocalizacao();
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/deletados")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Listar endereços deletados",
        description = "Busca histórico de endereços que foram deletados (soft delete). Acesso restrito a administradores."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de endereços deletados encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - apenas administradores")
    })
    public ResponseEntity<?> buscarEnderecosDeletados() {
        try {
            var enderecosDeletados = enderecoService.buscarEnderecosDeletados();
            return ResponseEntity.ok(enderecosDeletados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Classe interna para respostas de erro
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
