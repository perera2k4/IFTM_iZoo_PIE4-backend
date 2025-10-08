package com.backend.izoo.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.izoo.config.JwtTokenProvider;
import com.backend.izoo.dto.LoginRequestDTO;
import com.backend.izoo.dto.LoginResponseDTO;
import com.backend.izoo.dto.UsuarioDTO;
import com.backend.izoo.model.Usuario;
import com.backend.izoo.repositories.UsuarioRepository;

import jakarta.validation.Valid;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public UsuarioDTO buscarPorId(String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return new UsuarioDTO(usuario.get());
        }
        throw new RuntimeException("Usuário não encontrado com id: " + id);
    }

    public List<UsuarioDTO> buscarTodos() {
        List<Usuario> lista = usuarioRepository.findAll();
        return lista.stream().map(UsuarioDTO::new).toList();
    }

    public UsuarioDTO registrar(@Valid UsuarioDTO usuarioDTO) {
        // Verificar se login já existe
        if (usuarioRepository.existsByLogin(usuarioDTO.getLogin())) {
            throw new RuntimeException("Login já está em uso: " + usuarioDTO.getLogin());
        }

        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("Email já está em uso: " + usuarioDTO.getEmail());
        }

        // VALIDAÇÃO DE SEGURANÇA: Bloquear criação de ADMIN e AGENT via registro
        if (usuarioDTO.getCargo() != null) {
            String cargo = usuarioDTO.getCargo().trim().toUpperCase();
            if ("ADMIN".equals(cargo) || "AGENT".equals(cargo)) {
                throw new RuntimeException("Não é possível criar usuários com cargo ADMIN ou AGENT. Apenas USER é permitido no registro.");
            }
        }
        
        Usuario entidade = new Usuario();
        copiaDTOparaEntidade(usuarioDTO, entidade);
        
        // Definir cargo padrão como USER se não especificado
        if (entidade.getCargo() == null || entidade.getCargo().trim().isEmpty()) {
            entidade.setCargo("USER");
        }
        
        // Criptografar senha
        entidade.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        entidade.setCreatedAt(Instant.now());
        entidade.setUpdatedAt(Instant.now());
        
        entidade = usuarioRepository.save(entidade);
        return new UsuarioDTO(entidade);
    }

    public LoginResponseDTO login(@Valid LoginRequestDTO loginRequest) {
        // Buscar usuário por login
        Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(loginRequest.getLogin());
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar senha
        if (!passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        // Gerar token JWT
        String token = jwtTokenProvider.generateToken(
            usuario.getId(), 
            usuario.getLogin(), 
            usuario.getCargo()
        );

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        return new LoginResponseDTO("Login realizado com sucesso", token, usuarioDTO);
    }

    public UsuarioDTO atualizar(String id, @Valid UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario entidade = usuarioOpt.get();
            
            // Verificar se login está sendo alterado e se já existe
            if (!entidade.getLogin().equals(usuarioDTO.getLogin()) && 
                usuarioRepository.existsByLogin(usuarioDTO.getLogin())) {
                throw new RuntimeException("Login já está em uso: " + usuarioDTO.getLogin());
            }

            // Verificar se email está sendo alterado e se já existe
            if (!entidade.getEmail().equals(usuarioDTO.getEmail()) && 
                usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                throw new RuntimeException("Email já está em uso: " + usuarioDTO.getEmail());
            }

            // Preservar cargo original se não for admin (será controlado por endpoint específico)
            String cargoOriginal = entidade.getCargo();
            copiaDTOparaEntidade(usuarioDTO, entidade);
            
            // Restaurar cargo original - alteração de cargo deve ser feita pelo endpoint específico
            entidade.setCargo(cargoOriginal);
            
            // Criptografar nova senha se fornecida
            if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().trim().isEmpty()) {
                entidade.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            }
            
            entidade.setUpdatedAt(Instant.now());
            entidade = usuarioRepository.save(entidade);
            return new UsuarioDTO(entidade);
        }
        throw new RuntimeException("Usuário não encontrado com id: " + id);
    }

    public UsuarioDTO atualizarParcial(String id, @Valid UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario entidade = usuarioOpt.get();
            copiaDTOparaEntidadeParcial(usuarioDTO, entidade);
            entidade.setUpdatedAt(Instant.now());
            entidade = usuarioRepository.save(entidade);
            return new UsuarioDTO(entidade);
        }
        throw new RuntimeException("Usuário não encontrado com id: " + id);
    }

    public void deletar(String id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }
    }

    public List<UsuarioDTO> buscarPorCargo(String cargo) {
        List<Usuario> lista = usuarioRepository.findByCargo(cargo);
        return lista.stream().map(UsuarioDTO::new).toList();
    }

    private void copiaDTOparaEntidade(UsuarioDTO usuarioDTO, Usuario entidade) {
        entidade.setLogin(usuarioDTO.getLogin());
        // Se cargo não for fornecido ou for vazio, usar USER como padrão
        if (usuarioDTO.getCargo() != null && !usuarioDTO.getCargo().trim().isEmpty()) {
            String cargoUpper = usuarioDTO.getCargo().toUpperCase();
            // VALIDAÇÃO DE SEGURANÇA: Bloquear ADMIN e AGENT
            if ("ADMIN".equals(cargoUpper) || "AGENT".equals(cargoUpper)) {
                throw new RuntimeException("Não é possível definir cargo ADMIN ou AGENT via registro. Apenas USER é permitido.");
            }
            entidade.setCargo(cargoUpper);
        } else {
            entidade.setCargo("USER");
        }
        entidade.setEmail(usuarioDTO.getEmail());
        entidade.setTelefone(usuarioDTO.getTelefone());
        // Não copiar senha aqui - será tratada separadamente para criptografia
    }

    private void copiaDTOparaEntidadeParcial(UsuarioDTO usuarioDTO, Usuario entidade) {
        if (usuarioDTO.getLogin() != null && !usuarioDTO.getLogin().trim().isEmpty()) {
            // Verificar se novo login já existe
            if (!entidade.getLogin().equals(usuarioDTO.getLogin()) && 
                usuarioRepository.existsByLogin(usuarioDTO.getLogin())) {
                throw new RuntimeException("Login já está em uso: " + usuarioDTO.getLogin());
            }
            entidade.setLogin(usuarioDTO.getLogin());
        }
        
        // Permitir alteração de cargo apenas para administradores
        if (usuarioDTO.getCargo() != null && !usuarioDTO.getCargo().trim().isEmpty()) {
            // Verificar se o usuário atual é administrador
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
            
            if (isAdmin) {
                String cargoUpper = usuarioDTO.getCargo().toUpperCase();
                // Validar se o cargo é válido
                if (!cargoUpper.matches("ADMIN|USER|AGENT")) {
                    throw new RuntimeException("Cargo inválido. Deve ser 'ADMIN', 'USER' ou 'AGENT'");
                }
                entidade.setCargo(cargoUpper);
            } else {
                throw new RuntimeException("Apenas administradores podem alterar cargo de usuários");
            }
        }
        
        if (usuarioDTO.getEmail() != null && !usuarioDTO.getEmail().trim().isEmpty()) {
            // Verificar se novo email já existe
            if (!entidade.getEmail().equals(usuarioDTO.getEmail()) && 
                usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                throw new RuntimeException("Email já está em uso: " + usuarioDTO.getEmail());
            }
            entidade.setEmail(usuarioDTO.getEmail());
        }
        if (usuarioDTO.getTelefone() != null && !usuarioDTO.getTelefone().trim().isEmpty()) {
            entidade.setTelefone(usuarioDTO.getTelefone());
        }
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().trim().isEmpty()) {
            entidade.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        }
    }
}
