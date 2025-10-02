package com.backend.izoo.dto;

import java.io.Serializable;
import java.time.Instant;

import com.backend.izoo.model.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    @NotBlank(message = "Login é obrigatório")
    private String login;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @Pattern(regexp = "ADMIN|USER|SUPERVISOR|FUNCIONARIO", message = "Cargo deve ser 'ADMIN', 'USER', 'SUPERVISOR', 'FUNCIONARIO'")
    private String cargo = "USER";

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\+55\\d{2}9\\d{8}", message = "Telefone deve seguir o padrão +55XX9XXXXXXXX")
    private String telefone;

    private Instant createdAt;
    private Instant updatedAt;

    public UsuarioDTO() {}

    public UsuarioDTO(String id, String login, String senha, String cargo, String email, String telefone,
                     Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.cargo = cargo;
        this.email = email;
        this.telefone = telefone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UsuarioDTO(Usuario entidade) {
        this.id = entidade.getId();
        this.login = entidade.getLogin();
        // Não incluir senha no DTO de resposta por segurança
        this.cargo = entidade.getCargo();
        this.email = entidade.getEmail();
        this.telefone = entidade.getTelefone();
        this.createdAt = entidade.getCreatedAt();
        this.updatedAt = entidade.getUpdatedAt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
