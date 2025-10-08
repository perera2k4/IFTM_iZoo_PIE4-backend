package com.backend.izoo.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "enderecos_deletados")
public class EnderecoDeletado {
    
    @Id
    private String id;
    
    // Dados originais do endereço
    private String originalId;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private Double latitude;
    private Double longitude;
    private Instant createdAt;
    private Instant updatedAt;
    
    // Dados da deleção
    private Instant deletedAt;
    private String deletedBy; // ID do usuário que executou a deleção
    private String deleteReason; // Motivo da deleção (opcional)
    
    // Constructors
    public EnderecoDeletado() {}
    
    public EnderecoDeletado(Endereco endereco, String deletedBy, String deleteReason) {
        this.originalId = endereco.getId();
        this.rua = endereco.getRua();
        this.numero = endereco.getNumero();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.cep = endereco.getCep();
        this.latitude = endereco.getLatitude();
        this.longitude = endereco.getLongitude();
        this.createdAt = endereco.getCreatedAt();
        this.updatedAt = endereco.getUpdatedAt();
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
    
    public String getRua() {
        return rua;
    }
    
    public void setRua(String rua) {
        this.rua = rua;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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