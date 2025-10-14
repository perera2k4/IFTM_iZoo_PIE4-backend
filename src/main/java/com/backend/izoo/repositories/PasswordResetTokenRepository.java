package com.backend.izoo.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.backend.izoo.model.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
    
    /**
     * Busca token pelo valor do token
     */
    Optional<PasswordResetToken> findByToken(String token);
    
    /**
     * Busca todos os tokens de um email
     */
    List<PasswordResetToken> findByEmail(String email);
    
    /**
     * Busca tokens válidos (não usados e não expirados) de um email
     */
    List<PasswordResetToken> findByEmailAndUsedFalseAndExpiryDateAfter(String email, Instant now);
    
    /**
     * Delete tokens expirados
     */
    void deleteByExpiryDateBefore(Instant now);
    
    /**
     * Delete todos os tokens de um email
     */
    void deleteByEmail(String email);
}
