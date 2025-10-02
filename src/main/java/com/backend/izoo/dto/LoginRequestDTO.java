package com.backend.izoo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados necessários para autenticação do usuário")
public class LoginRequestDTO {
    
    @NotBlank(message = "Login é obrigatório")
    @Schema(description = "Nome de usuário para login", example = "bruno.pereira", requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;
    
    @NotBlank(message = "Senha é obrigatória")
    @Schema(description = "Senha do usuário", example = "MinhaSenh@123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String senha;
    
    public LoginRequestDTO() {}
    
    public LoginRequestDTO(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
}