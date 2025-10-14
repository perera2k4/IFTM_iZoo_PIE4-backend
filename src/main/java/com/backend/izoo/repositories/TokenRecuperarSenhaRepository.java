package com.backend.izoo.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.backend.izoo.model.TokenRecuperarSenha;

@Repository
public interface TokenRecuperarSenhaRepository extends MongoRepository<TokenRecuperarSenha, String> {
    
    /**
     * Busca token pelo valor do token
     */
    Optional<TokenRecuperarSenha> findByToken(String token);
    
    /**
     * Busca todos os tokens de um email
     */
    List<TokenRecuperarSenha> findByEmail(String email);
    
    /**
     * Busca tokens válidos (não usados e não expirados) de um email
     */
    List<TokenRecuperarSenha> findByEmailAndUsedFalseAndExpiryDateAfter(String email, Instant now);
    
    /**
     * Delete tokens expirados
     */
    void deleteByExpiryDateBefore(Instant now);
    
    /**
     * Delete todos os tokens de um email
     */
    void deleteByEmail(String email);
}
