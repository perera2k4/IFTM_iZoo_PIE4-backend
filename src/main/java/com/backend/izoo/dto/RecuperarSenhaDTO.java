package com.backend.izoo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para redefinição de senha com token
 */
public class RecuperarSenhaDTO {
    
    @NotBlank(message = "Token é obrigatório")
    private String token;

    @NotBlank(message = "Nova senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String novaSenha;

    public RecuperarSenhaDTO() {}

    public RecuperarSenhaDTO(String token, String novaSenha) {
        this.token = token;
        this.novaSenha = novaSenha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    @Override
    public String toString() {
        return "ResetPasswordDTO{" +
                "token='" + token + '\'' +
                ", novaSenha='[PROTECTED]'" +
                '}';
    }
}
