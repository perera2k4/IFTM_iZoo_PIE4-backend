package com.backend.izoo.service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.izoo.model.PasswordResetToken;
import com.backend.izoo.model.Usuario;
import com.backend.izoo.repositories.PasswordResetTokenRepository;
import com.backend.izoo.repositories.UsuarioRepository;

/**
 * Serviço para gerenciamento de recuperação de senha
 */
@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Cria um token de recuperação e envia por email
     * 
     * @param email Email do usuário
     * @return true se o email foi enviado com sucesso
     * @throws RuntimeException se o usuário não for encontrado
     */
    @Transactional
    public boolean solicitarRecuperacaoSenha(String email) {
        // Verifica se o usuário existe
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com o email: " + email);
        }

        // Invalida tokens anteriores do mesmo email (opcional, mas recomendado)
        invalidarTokensAntigos(email);

        // Gera código de 6 dígitos
        String codigo = gerarCodigoRecuperacao();

        // Cria o token no banco
        PasswordResetToken token = new PasswordResetToken(codigo, email);
        tokenRepository.save(token);

        // Envia email
        try {
            emailService.enviarEmailRecuperacaoSenha(email, codigo);
            return true;
        } catch (Exception e) {
            // Se falhar ao enviar email, remove o token
            tokenRepository.delete(token);
            throw new RuntimeException("Erro ao enviar email de recuperação", e);
        }
    }

    /**
     * Valida se um token é válido
     * 
     * @param tokenValue Valor do token
     * @return true se o token é válido
     */
    public boolean validarToken(String tokenValue) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(tokenValue);
        
        if (tokenOpt.isEmpty()) {
            return false;
        }

        PasswordResetToken token = tokenOpt.get();
        return token.isValid();
    }

    /**
     * Redefine a senha do usuário usando o token
     * 
     * @param tokenValue Valor do token
     * @param novaSenha Nova senha
     * @return true se a senha foi redefinida com sucesso
     * @throws RuntimeException se o token for inválido ou expirado
     */
    @Transactional
    public boolean redefinirSenha(String tokenValue, String novaSenha) {
        // Busca o token
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(tokenValue);
        
        if (tokenOpt.isEmpty()) {
            throw new RuntimeException("Token inválido");
        }

        PasswordResetToken token = tokenOpt.get();

        // Verifica se o token é válido
        if (!token.isValid()) {
            if (token.isExpired()) {
                throw new RuntimeException("Token expirado");
            } else {
                throw new RuntimeException("Token já foi utilizado");
            }
        }

        // Busca o usuário
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(token.getEmail());
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        // Atualiza a senha
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        // Marca o token como usado
        token.setUsed(true);
        tokenRepository.save(token);

        return true;
    }

    /**
     * Gera um código de recuperação de 6 dígitos
     */
    private String gerarCodigoRecuperacao() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000); // Gera número entre 100000 e 999999
        return String.valueOf(codigo);
    }

    /**
     * Invalida tokens antigos de um email
     */
    private void invalidarTokensAntigos(String email) {
        var tokens = tokenRepository.findByEmailAndUsedFalseAndExpiryDateAfter(email, Instant.now());
        tokens.forEach(token -> {
            token.setUsed(true);
            tokenRepository.save(token);
        });
    }

    /**
     * Limpa tokens expirados do banco (pode ser executado periodicamente)
     */
    @Transactional
    public void limparTokensExpirados() {
        tokenRepository.deleteByExpiryDateBefore(Instant.now());
    }
}
