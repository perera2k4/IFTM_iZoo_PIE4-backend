package com.backend.izoo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para validação do token de recuperação
 */
public class ValidarTokenDTO {
    
    @NotBlank(message = "Token é obrigatório")
    private String token;

    public ValidarTokenDTO() {}

    public ValidarTokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ValidateTokenDTO{" +
                "token='" + token + '\'' +
                '}';
    }
}
