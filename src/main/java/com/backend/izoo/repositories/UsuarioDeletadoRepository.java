package com.backend.izoo.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.izoo.model.UsuarioDeletado;

@Repository
public interface UsuarioDeletadoRepository extends MongoRepository<UsuarioDeletado, String> {
    
    // Buscar por ID original do usuário
    List<UsuarioDeletado> findByOriginalId(String originalId);
    
    // Buscar por quem deletou
    List<UsuarioDeletado> findByDeletedBy(String deletedBy);
    
    // Buscar por período de deleção
    List<UsuarioDeletado> findByDeletedAtBetween(Instant startDate, Instant endDate);
    
    // Buscar por login (para histórico)
    List<UsuarioDeletado> findByLogin(String login);
    
    // Buscar por cargo
    List<UsuarioDeletado> findByCargo(String cargo);
    
    // Buscar registros mais recentes
    @Query(value = "{}", sort = "{ deletedAt : -1 }")
    List<UsuarioDeletado> findAllOrderByDeletedAtDesc();
    
    // Contar deletions por período
    Long countByDeletedAtBetween(Instant startDate, Instant endDate);
}