package com.backend.izoo.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios_deletados")
public class UsuarioDeletado {
    
    @Id
    private String id;
    
    // Dados originais do usuário
    private String originalId;
    private String login;
    private String senha;
    private String cargo;
    private String email;
    private String telefone;
    private Instant createdAt;
    private Instant updatedAt;
    
    // Dados da deleção
    private Instant deletedAt;
    private String deletedBy; // ID do usuário que executou a deleção
    private String deleteReason; // Motivo da deleção (opcional)
    
    // Constructors
    public UsuarioDeletado() {}
    
    public UsuarioDeletado(Usuario usuario, String deletedBy, String deleteReason) {
        this.originalId = usuario.getId();
        this.login = usuario.getLogin();
        this.senha = usuario.getSenha();
        this.cargo = usuario.getCargo();
        this.email = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.createdAt = usuario.getCreatedAt();
        this.updatedAt = usuario.getUpdatedAt();
        this.deletedAt = Instant.now();
        this.deletedBy = deletedBy;
        this.deleteReason = deleteReason;
    }
    
    // Getters e Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getOriginalId() {
        return originalId;
    }
    
    public void setOriginalId(String originalId) {
        this.originalId = originalId;
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
    
    public Instant getDeletedAt() {
        return deletedAt;
    }
    
    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
    
    public String getDeletedBy() {
        return deletedBy;
    }
    
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
    
    public String getDeleteReason() {
        return deleteReason;
    }
    
    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }
}