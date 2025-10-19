package com.backend.izoo.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.backend.izoo.model.Inspecao;

@Repository
public interface InspecaoRepository extends MongoRepository<Inspecao, String> {
    
    // Buscar por tipo
    List<Inspecao> findByTipoIgnoreCase(String tipo);
    
    // Buscar por endereço ID
    List<Inspecao> findByEnderecoId(String enderecoId);
    
    // Buscar por gravidade
    List<Inspecao> findByGravidadeIgnoreCase(String gravidade);
    
    // Buscar por status
    List<Inspecao> findByStatusIgnoreCase(String status);
    
    // Buscar por tipo e endereço ID
    List<Inspecao> findByTipoIgnoreCaseAndEnderecoId(String tipo, String enderecoId);
    
    // Buscar por status e gravidade
    List<Inspecao> findByStatusIgnoreCaseAndGravidadeIgnoreCase(String status, String gravidade);
    
    // Buscar por período de criação
    List<Inspecao> findByCreatedAtBetween(Instant startDate, Instant endDate);
    
    // Buscar por endereço ID e status
    List<Inspecao> findByEnderecoIdAndStatusIgnoreCase(String enderecoId, String status);
    
    // Buscar por criador
    List<Inspecao> findByCriadoPor(String criadoPor);
    
    // Buscar por criador e status
    List<Inspecao> findByCriadoPorAndStatusIgnoreCase(String criadoPor, String status);
}
