package com.backend.izoo.dto;

import java.io.Serializable;
import java.time.Instant;

import com.backend.izoo.model.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnderecoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    @NotBlank(message = "Rua é obrigatória")
    private String rua;

    @NotNull(message = "Número é obrigatório")
    private String numero;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    private Double latitude;
    private Double longitude;
    private Instant createdAt;
    private Instant updatedAt;

    public EnderecoDTO() {}

    public EnderecoDTO(String id, String rua, String numero, String bairro, String cidade, String estado,
                      Double latitude, Double longitude, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public EnderecoDTO(Endereco entidade) {
        this.id = entidade.getId();
        this.rua = entidade.getRua();
        this.numero = entidade.getNumero();
        this.bairro = entidade.getBairro();
        this.cidade = entidade.getCidade();
        this.estado = entidade.getEstado();
        this.latitude = entidade.getLatitude();
        this.longitude = entidade.getLongitude();
        this.createdAt = entidade.getCreatedAt();
        this.updatedAt = entidade.getUpdatedAt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
