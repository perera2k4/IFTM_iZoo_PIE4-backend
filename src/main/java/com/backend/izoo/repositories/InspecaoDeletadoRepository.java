package com.backend.izoo.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.izoo.model.InspecaoDeletado;

@Repository
public interface InspecaoDeletadoRepository extends MongoRepository<InspecaoDeletado, String> {
    
    // Buscar por ID original da inspeção
    List<InspecaoDeletado> findByOriginalId(String originalId);
    
    // Buscar por quem deletou
    List<InspecaoDeletado> findByDeletedBy(String deletedBy);
    
    // Buscar por período de deleção
    List<InspecaoDeletado> findByDeletedAtBetween(Instant startDate, Instant endDate);
    
    // Buscar por tipo (para histórico)
    List<InspecaoDeletado> findByTipoIgnoreCase(String tipo);
    
    // Buscar por endereço ID
    List<InspecaoDeletado> findByEnderecoId(String enderecoId);
    
    // Buscar por gravidade
    List<InspecaoDeletado> findByGravidadeIgnoreCase(String gravidade);
    
    // Buscar registros mais recentes
    @Query(value = "{}", sort = "{ deletedAt : -1 }")
    List<InspecaoDeletado> findAllOrderByDeletedAtDesc();
    
    // Contar deletions por período
    Long countByDeletedAtBetween(Instant startDate, Instant endDate);
}
