package com.backend.izoo.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.izoo.model.EnderecoDeletado;

@Repository
public interface EnderecoDeletadoRepository extends MongoRepository<EnderecoDeletado, String> {
    
    // Buscar por ID original do endereço
    List<EnderecoDeletado> findByOriginalId(String originalId);
    
    // Buscar por quem deletou
    List<EnderecoDeletado> findByDeletedBy(String deletedBy);
    
    // Buscar por período de deleção
    List<EnderecoDeletado> findByDeletedAtBetween(Instant startDate, Instant endDate);
    
    // Buscar por CEP (para histórico)
    List<EnderecoDeletado> findByCep(String cep);
    
    // Buscar por cidade
    List<EnderecoDeletado> findByCidade(String cidade);
    
    // Buscar por estado
    List<EnderecoDeletado> findByEstado(String estado);
    
    // Buscar registros mais recentes
    @Query(value = "{}", sort = "{ deletedAt : -1 }")
    List<EnderecoDeletado> findAllOrderByDeletedAtDesc();
    
    // Contar deletions por período
    Long countByDeletedAtBetween(Instant startDate, Instant endDate);
}