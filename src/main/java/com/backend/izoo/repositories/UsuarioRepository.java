package com.backend.izoo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.backend.izoo.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    // Buscar usuário por login
    Optional<Usuario> findByLogin(String login);

    // Buscar usuário por email
    Optional<Usuario> findByEmail(String email);

    // Verificar se login já existe
    boolean existsByLogin(String login);

    // Verificar se email já existe
    boolean existsByEmail(String email);

    // Buscar usuários por cargo
    List<Usuario> findByCargo(String cargo);

    // Buscar usuários por login ou email
    Optional<Usuario> findByLoginOrEmail(String login, String email);
}
