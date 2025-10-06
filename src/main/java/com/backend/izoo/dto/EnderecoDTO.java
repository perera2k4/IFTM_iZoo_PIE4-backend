package com.backend.izoo.dto;

import java.io.Serializable;
import java.time.Instant;

import com.backend.izoo.model.Endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados do endereço para transferência de dados")
public class EnderecoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID único do endereço", accessMode = Schema.AccessMode.READ_ONLY, example = "507f1f77bcf86cd799439011")
    private String id;

    @NotBlank(message = "Rua é obrigatória")
    @Schema(description = "Nome da rua", example = "Rua das Flores", requiredMode = Schema.RequiredMode.REQUIRED)
    private String rua;

    @NotNull(message = "Número é obrigatório")
    @Schema(description = "Número do endereço", example = "123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numero;

    @NotBlank(message = "Bairro é obrigatório")
    @Schema(description = "Nome do bairro", example = "Centro", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bairro;
    
    @NotBlank(message = "Cidade é obrigatória")
    @Schema(description = "Nome da cidade", example = "Uberlândia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Schema(description = "Sigla do estado (UF)", example = "MG", requiredMode = Schema.RequiredMode.REQUIRED)
    private String estado;
    
    @NotBlank(message = "CEP é obrigatório")
    @Schema(description = "Número do CEP", example = "38307-096", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cep;

    @Schema(description = "Latitude da localização", example = "-18.9111")
    private Double latitude;
    
    @Schema(description = "Longitude da localização", example = "-48.2611")
    private Double longitude;
    
    @Schema(description = "Data e hora de criação do endereço", accessMode = Schema.AccessMode.READ_ONLY)
    private Instant createdAt;
    
    @Schema(description = "Data e hora da última atualização", accessMode = Schema.AccessMode.READ_ONLY)
    private Instant updatedAt;

    public EnderecoDTO() {}

    public EnderecoDTO(String id, String rua, String numero, String bairro, String cidade, String estado, String cep, Double latitude, Double longitude, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
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
        this.cep = entidade.getCep();
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
}
