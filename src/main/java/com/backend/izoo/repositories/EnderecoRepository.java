package com.backend.izoo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.izoo.model.Endereco;

@Repository
public interface EnderecoRepository extends MongoRepository<Endereco, String> {

    // Buscar endereços por cidade
    List<Endereco> findByCidadeIgnoreCase(String cidade);

    // Buscar endereços por estado
    List<Endereco> findByEstadoIgnoreCase(String estado);

    // Buscar endereços por cidade e estado
    List<Endereco> findByCidadeIgnoreCaseAndEstadoIgnoreCase(String cidade, String estado);

    // Buscar endereços por bairro
    List<Endereco> findByBairroIgnoreCase(String bairro);

    // Buscar endereços que contenham latitude e longitude (não nulos)
    @Query("{ 'latitude': { $ne: null }, 'longitude': { $ne: null } }")
    List<Endereco> findByLatitudeAndLongitudeNotNull();
}
