package com.backend.izoo.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inspecoes_deletadas")
public class InspecaoDeletado {
    
    @Id
    private String id;
    
    // Dados originais da inspeção
    private String originalId;
    private String tipo;
    private String bairro;
    private String gravidade;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    
    // Dados da deleção
    private Instant deletedAt;
    private String deletedBy; // ID do usuário que executou a deleção
    private String deleteReason; // Motivo da deleção (opcional)
    
    // Construtores
    public InspecaoDeletado() {}
    
    public InspecaoDeletado(Inspecao inspecao, String deletedBy, String deleteReason) {
        this.originalId = inspecao.getId();
        this.tipo = inspecao.getTipo();
        this.bairro = inspecao.getBairro();
        this.gravidade = inspecao.getGravidade();
        this.status = inspecao.getStatus();
        this.createdAt = inspecao.getCreatedAt();
        this.updatedAt = inspecao.getUpdatedAt();
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
