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

import com.backend.izoo.dto.InspecaoDTO;
import com.backend.izoo.service.InspecaoService;

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
@RequestMapping(value = "/inspecao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
@Tag(name = "Inspeções", description = "Endpoints para gerenciamento de inspeções sanitárias")
@SecurityRequirement(name = "Bearer Authentication")
public class InspecaoController {

    @Autowired
    private InspecaoService inspecaoService;

    @GetMapping
    @Operation(
        summary = "Listar todas as inspeções",
        description = "Retorna uma lista com todas as inspeções cadastradas. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inspeções retornada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InspecaoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<InspecaoDTO>> buscarTodos() {
        List<InspecaoDTO> lista = inspecaoService.buscarTodos();
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar inspeção por ID",
        description = "Retorna uma inspeção específica pelo seu identificador único. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inspeção encontrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InspecaoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Inspeção não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<InspecaoDTO> buscarPorId(
            @Parameter(description = "ID da inspeção", required = true) 
            @PathVariable String id) {
        InspecaoDTO inspecao = inspecaoService.buscarPorId(id);
        return ResponseEntity.ok().body(inspecao);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(
        summary = "Criar nova inspeção",
        description = "Cria uma nova inspeção no sistema. Acesso restrito a ADMIN e AGENT."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Inspeção criada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InspecaoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - apenas ADMIN ou AGENT")
    })
    public ResponseEntity<InspecaoDTO> inserir(
            @Valid @RequestBody InspecaoDTO inspecaoDTO) {
        InspecaoDTO novaInspecao = inspecaoService.inserir(inspecaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaInspecao);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(
        summary = "Atualizar inspeção completa",
        description = "Atualiza todos os dados de uma inspeção existente. Acesso restrito a ADMIN e AGENT."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inspeção atualizada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InspecaoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Inspeção não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - apenas ADMIN ou AGENT")
    })
    public ResponseEntity<InspecaoDTO> atualizar(
            @Parameter(description = "ID da inspeção", required = true) 
            @PathVariable String id,
            @Valid @RequestBody InspecaoDTO inspecaoDTO) {
        InspecaoDTO inspecaoAtualizada = inspecaoService.atualizar(id, inspecaoDTO);
        return ResponseEntity.ok().body(inspecaoAtualizada);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(
        summary = "Atualizar inspeção parcialmente",
        description = "Atualiza apenas os campos fornecidos de uma inspeção. Acesso restrito a ADMIN e AGENT."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inspeção atualizada parcialmente com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InspecaoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Inspeção não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - apenas ADMIN ou AGENT")
    })
    public ResponseEntity<InspecaoDTO> atualizarParcial(
            @Parameter(description = "ID da inspeção", required = true) 
            @PathVariable String id,
            @RequestBody InspecaoDTO inspecaoDTO) {
        InspecaoDTO inspecaoAtualizada = inspecaoService.atualizarParcial(id, inspecaoDTO);
        return ResponseEntity.ok().body(inspecaoAtualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Deletar inspeção",
        description = "Remove uma inspeção do sistema. Acesso restrito a ADMIN."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Inspeção deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Inspeção não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - apenas ADMIN")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da inspeção", required = true) 
            @PathVariable String id) {
        inspecaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(
        summary = "Buscar inspeções por tipo",
        description = "Retorna lista de inspeções filtradas por tipo. Tipos válidos: 'Foco de dengue', 'Animal peçonhento', 'Roedores', 'Outros'."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inspeções filtrada por tipo",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InspecaoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<InspecaoDTO>> buscarPorTipo(
            @Parameter(description = "Tipo da inspeção", required = true) 
            @PathVariable String tipo) {
        List<InspecaoDTO> lista = inspecaoService.buscarPorTipo(tipo);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/bairro/{bairro}")
    @Operation(
        summary = "Buscar inspeções por bairro",
        description = "Retorna lista de inspeções filtradas por bairro. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inspeções filtrada por bairro",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InspecaoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<InspecaoDTO>> buscarPorBairro(
            @Parameter(description = "Nome do bairro", required = true) 
            @PathVariable String bairro) {
        List<InspecaoDTO> lista = inspecaoService.buscarPorBairro(bairro);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/gravidade/{gravidade}")
    @Operation(
        summary = "Buscar inspeções por gravidade",
        description = "Retorna lista de inspeções filtradas por gravidade. Valores válidos: 'baixa', 'moderada', 'alta', 'gravissima'."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inspeções filtrada por gravidade",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InspecaoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<InspecaoDTO>> buscarPorGravidade(
            @Parameter(description = "Nível de gravidade", required = true) 
            @PathVariable String gravidade) {
        List<InspecaoDTO> lista = inspecaoService.buscarPorGravidade(gravidade);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/status/{status}")
    @Operation(
        summary = "Buscar inspeções por status",
        description = "Retorna lista de inspeções filtradas por status. Valores válidos: 'pendente', 'em andamento', 'concluído'."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inspeções filtrada por status",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InspecaoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<InspecaoDTO>> buscarPorStatus(
            @Parameter(description = "Status da inspeção", required = true) 
            @PathVariable String status) {
        List<InspecaoDTO> lista = inspecaoService.buscarPorStatus(status);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/filtro")
    @Operation(
        summary = "Buscar inspeções por bairro e status",
        description = "Retorna lista de inspeções filtradas por bairro e status. Requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inspeções filtrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InspecaoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Token inválido ou ausente")
    })
    public ResponseEntity<List<InspecaoDTO>> buscarPorBairroEStatus(
            @Parameter(description = "Nome do bairro", required = true) 
            @RequestParam String bairro,
            @Parameter(description = "Status da inspeção", required = true) 
            @RequestParam String status) {
        List<InspecaoDTO> lista = inspecaoService.buscarPorBairroEStatus(bairro, status);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/deletadas")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Listar inspeções deletadas",
        description = "Busca histórico de inspeções que foram deletadas (soft delete). Acesso restrito a administradores."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inspeções deletadas encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - apenas administradores")
    })
    public ResponseEntity<?> buscarInspecoesDeletadas() {
        try {
            var inspecoesDeletadas = inspecaoService.buscarInspecoesDeletadas();
            return ResponseEntity.ok(inspecoesDeletadas);
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

