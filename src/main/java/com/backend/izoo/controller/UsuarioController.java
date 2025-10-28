package com.backend.izoo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.izoo.dto.SolicitacaoLoginDTO;
import com.backend.izoo.dto.RespostaLoginDTO;
import com.backend.izoo.dto.UsuarioDTO;
import com.backend.izoo.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários e autenticação")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoints públicos de autenticação
    @PostMapping("/registro")
    @Operation(
        summary = "Registrar novo usuário",
        description = "Cria um novo usuário no sistema. Cargo padrão: USER. Apenas cargo USER é permitido no registro. Outros cargos são definidos manualmente por admins. Endpoint público que não requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário já existe",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> registrar(
            @Parameter(description = "Dados do usuário a ser registrado", required = true)
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO novoUsuario = usuarioService.registrar(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Erro ao registrar usuário: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(
        summary = "Fazer login",
        description = "Autentica o usuário e retorna um token JWT. Endpoint público que não requer autenticação."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespostaLoginDTO.class))),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> login(
            @Parameter(description = "Credenciais de login", required = true)
            @Valid @RequestBody SolicitacaoLoginDTO loginRequest) {
        try {
            RespostaLoginDTO response = usuarioService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Erro ao fazer login: " + e.getMessage()));
        }
    }

    // Endpoints protegidos - CRUD completo
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name.equals(#id)")
    @Operation(
        summary = "Buscar usuário por ID",
        description = "Retorna os dados de um usuário específico. Requer autenticação e autorização.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable String id) {
        try {
            UsuarioDTO usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Usuário não encontrado: " + e.getMessage()));
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(
        summary = "Listar todos os usuários",
        description = "Retorna uma lista com todos os usuários cadastrados. Requer permissão de ADMIN ou AGENT.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Apenas ADMIN ou AGENT")
    })
    public ResponseEntity<List<UsuarioDTO>> buscarTodos() {
        List<UsuarioDTO> lista = usuarioService.buscarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/cargo")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Buscar usuários por cargo",
        description = "Retorna uma lista de usuários filtrados por cargo. Requer permissão de administrador.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários filtrada por cargo",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Apenas administradores")
    })
    public ResponseEntity<List<UsuarioDTO>> buscarPorCargo(
            @Parameter(description = "Cargo para filtrar usuários (ADMIN, USER, AGENT)", required = true)
            @RequestParam String cargo) {
        List<UsuarioDTO> lista = usuarioService.buscarPorCargo(cargo);
        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name.equals(#id)")
    @Operation(
        summary = "Atualizar usuário",
        description = "Atualiza os dados de um usuário. Apenas administradores podem alterar qualquer campo de qualquer usuário, incluindo cargo. Usuários comuns só podem editar seus próprios dados básicos (exceto cargo).",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> atualizarUsuario(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable String id, 
            @Parameter(description = "Dados para atualização", required = true)
            @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO usuarioAtualizado = usuarioService.atualizarParcial(id, usuarioDTO);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Erro ao atualizar usuário: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Deletar usuário",
        description = "Remove um usuário do sistema. Requer permissão de administrador.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Apenas administradores")
    })
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do usuário a ser deletado", required = true)
            @PathVariable String id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.ok(new SuccessResponse("Usuário deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Erro ao deletar usuário: " + e.getMessage()));
        }
    }

    // Endpoint de teste para verificar autenticação
    @GetMapping("/teste-auth")
    @Operation(
        summary = "Testar autenticação",
        description = "Endpoint para testar se a autenticação JWT está funcionando. Retorna informações do usuário autenticado.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticação funcionando",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Token inválido ou ausente")
    })
    public ResponseEntity<?> testeAuth(HttpServletRequest request) {
        try {
            String userId = (String) request.getAttribute("userId");
            String login = (String) request.getAttribute("login");
            String cargo = (String) request.getAttribute("cargo");
            
            return ResponseEntity.ok(Map.of(
                "message", "Autenticação funcionando!",
                "userId", userId != null ? userId : "N/A",
                "login", login != null ? login : "N/A",
                "cargo", cargo != null ? cargo : "N/A",
                "timestamp", System.currentTimeMillis()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro no teste de autenticação: " + e.getMessage()));
        }
    }

    // Endpoint para histórico de usuários deletados (apenas admins)
    @GetMapping("/deletados")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
        summary = "Listar usuários deletados",
        description = "Busca histórico de usuários que foram deletados (soft delete). Acesso restrito a administradores."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários deletados encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - apenas administradores")
    })
    public ResponseEntity<?> buscarUsuariosDeletados() {
        try {
            var usuariosDeletados = usuarioService.buscarUsuariosDeletados();
            return ResponseEntity.ok(usuariosDeletados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Classes internas para respostas padronizadas
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

    public static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
