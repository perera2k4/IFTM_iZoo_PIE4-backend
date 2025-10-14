package com.backend.izoo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta do login com token JWT e dados do usuário")
public class RespostaLoginDTO {
    
    @Schema(description = "Mensagem de sucesso do login", example = "Login realizado com sucesso")
    private String message;
    
    @Schema(description = "Token JWT para autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "Tipo do token", example = "Bearer")
    private String tipo = "Bearer";
    
    @Schema(description = "Dados do usuário logado")
    private UsuarioDTO usuario;
    
    public RespostaLoginDTO() {}
    
    public RespostaLoginDTO(String message, String token, UsuarioDTO usuario) {
        this.message = message;
        this.token = token;
        this.usuario = usuario;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public UsuarioDTO getUsuario() {
        return usuario;
    }
    
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}