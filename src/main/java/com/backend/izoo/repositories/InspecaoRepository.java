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
    
    // Buscar por bairro
    List<Inspecao> findByBairroIgnoreCase(String bairro);
    
    // Buscar por gravidade
    List<Inspecao> findByGravidadeIgnoreCase(String gravidade);
    
    // Buscar por status
    List<Inspecao> findByStatusIgnoreCase(String status);
    
    // Buscar por tipo e bairro
    List<Inspecao> findByTipoIgnoreCaseAndBairroIgnoreCase(String tipo, String bairro);
    
    // Buscar por status e gravidade
    List<Inspecao> findByStatusIgnoreCaseAndGravidadeIgnoreCase(String status, String gravidade);
    
    // Buscar por período de criação
    List<Inspecao> findByCreatedAtBetween(Instant startDate, Instant endDate);
    
    // Buscar por bairro e status
    List<Inspecao> findByBairroIgnoreCaseAndStatusIgnoreCase(String bairro, String status);
}
