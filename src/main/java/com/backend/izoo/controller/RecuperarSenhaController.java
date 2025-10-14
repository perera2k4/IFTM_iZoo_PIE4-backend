package com.backend.izoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.izoo.dto.SolicitacaoRecuperarSenhaDTO;
import com.backend.izoo.dto.RecuperarSenhaDTO;
import com.backend.izoo.dto.ValidarTokenDTO;
import com.backend.izoo.service.RecuperarSenhaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller para gerenciar recuperação de senha
 */
@RestController
@RequestMapping("/recuperacao-senha")
@Tag(name = "Recuperação de Senha", description = "Endpoints para recuperação de senha com código de verificação por email")
public class RecuperarSenhaController {

    @Autowired
    private RecuperarSenhaService passwordResetService;

    /**
     * Solicita recuperação de senha - envia código por email
     */
    @PostMapping("/solicitar")
    @Operation(summary = "Solicitar recuperação de senha", 
               description = "Envia um código de 6 dígitos para o email informado. O código é válido por 15 minutos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email enviado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro ao enviar email")
    })
    public ResponseEntity<?> solicitarRecuperacao(@Valid @RequestBody SolicitacaoRecuperarSenhaDTO request) {
        try {
            passwordResetService.solicitarRecuperacaoSenha(request.getEmail());
            return ResponseEntity.ok(new SuccessResponse(
                "Código de recuperação enviado para o email: " + request.getEmail()
            ));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Usuário não encontrado com o email informado"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro ao enviar email de recuperação: " + e.getMessage()));
        }
    }

    /**
     * Valida se um código/token é válido
     */
    @PostMapping("/validar-token")
    @Operation(summary = "Validar token de recuperação", 
               description = "Verifica se um código de recuperação é válido e não expirou")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token válido"),
        @ApiResponse(responseCode = "400", description = "Token inválido ou expirado")
    })
    public ResponseEntity<?> validarToken(@Valid @RequestBody ValidarTokenDTO request) {
        boolean isValid = passwordResetService.validarToken(request.getToken());
        
        if (isValid) {
            return ResponseEntity.ok(new SuccessResponse("Token válido"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Token inválido ou expirado"));
        }
    }

    /**
     * Redefine a senha usando o token
     */
    @PostMapping("/redefinir")
    @Operation(summary = "Redefinir senha", 
               description = "Redefine a senha do usuário usando o código de recuperação válido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
        @ApiResponse(responseCode = "400", description = "Token inválido ou expirado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<?> redefinirSenha(@Valid @RequestBody RecuperarSenhaDTO request) {
        try {
            passwordResetService.redefinirSenha(request.getToken(), request.getNovaSenha());
            return ResponseEntity.ok(new SuccessResponse("Senha redefinida com sucesso"));
        } catch (RuntimeException e) {
            String mensagem = e.getMessage();
            
            if (mensagem.contains("inválido") || mensagem.contains("expirado") || mensagem.contains("utilizado")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(mensagem));
            } else if (mensagem.contains("não encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(mensagem));
            }
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro ao redefinir senha: " + mensagem));
        }
    }

    /*
    * Classe interna para respostas de sucesso
    */
    private static class SuccessResponse {
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

    /**
     * Classe interna para respostas de erro
     */
    private static class ErrorResponse {
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
