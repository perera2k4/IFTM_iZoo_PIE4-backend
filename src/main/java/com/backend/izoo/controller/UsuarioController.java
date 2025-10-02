package com.backend.izoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.backend.izoo.dto.LoginRequestDTO;
import com.backend.izoo.dto.LoginResponseDTO;
import com.backend.izoo.dto.UsuarioDTO;
import com.backend.izoo.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoints públicos de autenticação
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO novoUsuario = usuarioService.registrar(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Erro ao registrar usuário: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            LoginResponseDTO response = usuarioService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Erro ao fazer login: " + e.getMessage()));
        }
    }

    // Endpoints protegidos - CRUD completo
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin') or authentication.principal.id == #id")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        try {
            UsuarioDTO usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Usuário não encontrado: " + e.getMessage()));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<UsuarioDTO>> buscarTodos() {
        List<UsuarioDTO> lista = usuarioService.buscarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/cargo")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<UsuarioDTO>> buscarPorCargo(@RequestParam String cargo) {
        List<UsuarioDTO> lista = usuarioService.buscarPorCargo(cargo);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin') or authentication.principal.id == #id")
    public ResponseEntity<?> atualizar(@PathVariable String id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO usuarioAtualizado = usuarioService.atualizar(id, usuarioDTO);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Erro ao atualizar usuário: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('admin') or authentication.principal.id == #id")
    public ResponseEntity<?> atualizarParcial(@PathVariable String id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO usuarioAtualizado = usuarioService.atualizarParcial(id, usuarioDTO);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Erro ao atualizar usuário: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.ok(new SuccessResponse("Usuário deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Erro ao deletar usuário: " + e.getMessage()));
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
