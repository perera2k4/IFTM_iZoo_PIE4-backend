package com.backend.izoo.model;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

/**
 * Modelo para armazenar tokens de recuperação de senha
 * Cada token é válido por 15 minutos
 */
@Document(collection = "password_reset_tokens")
public class PasswordResetToken implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Tempo de expiração: 15 minutos
    private static final long EXPIRATION_TIME_MS = 15 * 60 * 1000;

    @Id
    private String id;

    @NotBlank(message = "Token é obrigatório")
    @Indexed(unique = true)
    private String token;

    @NotBlank(message = "Email é obrigatório")
    @Indexed
    private String email;

    private Instant expiryDate;
    
    private boolean used = false;

    @CreatedDate
    private Instant createdAt;

    public PasswordResetToken() {}

    public PasswordResetToken(String token, String email) {
        this.token = token;
        this.email = email;
        this.expiryDate = Instant.now().plusMillis(EXPIRATION_TIME_MS);
        this.used = false;
    }

    /**
     * Verifica se o token expirou
     */
    public boolean isExpired() {
        return Instant.now().isAfter(this.expiryDate);
    }

    /**
     * Verifica se o token é válido (não expirou e não foi usado)
     */
    public boolean isValid() {
        return !isExpired() && !used;
    }

    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", expiryDate=" + expiryDate +
                ", used=" + used +
                ", createdAt=" + createdAt +
                '}';
    }
}
