package com.backend.izoo.dto;

import java.time.Instant;

import com.backend.izoo.model.Inspecao;

import jakarta.validation.constraints.NotBlank;

public class InspecaoDTO {
    
    private String id;
    
    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;
    
    @NotBlank(message = "Endereço ID é obrigatório")
    private String enderecoId;
    
    @NotBlank(message = "Gravidade é obrigatória")
    private String gravidade;
    
    @NotBlank(message = "Status é obrigatório")
    private String status;
    
    private Instant createdAt;
    private Instant updatedAt;
    
    // Construtores
    public InspecaoDTO() {}
    
    public InspecaoDTO(Inspecao entidade) {
        this.id = entidade.getId();
        this.tipo = entidade.getTipo();
        this.enderecoId = entidade.getEnderecoId();
        this.gravidade = entidade.getGravidade();
        this.status = entidade.getStatus();
        this.createdAt = entidade.getCreatedAt();
        this.updatedAt = entidade.getUpdatedAt();
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
    
    public String getEnderecoId() {
        return enderecoId;
    }
    
    public void setEnderecoId(String enderecoId) {
        this.enderecoId = enderecoId;
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
