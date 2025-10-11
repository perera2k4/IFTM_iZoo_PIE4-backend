package com.backend.izoo.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "inspecoes")
public class Inspecao {
    
    @Id
    private String id;
    
    @NotBlank(message = "Tipo é obrigatório")
    private String tipo; // Foco de dengue, animal peçonhento, roedores, outros
    
    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;
    
    @NotBlank(message = "Gravidade é obrigatória")
    private String gravidade; // baixa, moderada, alta, gravissima
    
    @NotBlank(message = "Status é obrigatório")
    private String status; // pendente, em andamento, concluído
    
    @NotNull
    private Instant createdAt;
    
    private Instant updatedAt;
    
    // Construtores
    public Inspecao() {}
    
    public Inspecao(String tipo, String bairro, String gravidade, String status) {
        this.tipo = tipo;
        this.bairro = bairro;
        this.gravidade = gravidade;
        this.status = status;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    // Getters e Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public String getGravidade() {
        return gravidade;
    }
    
    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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
