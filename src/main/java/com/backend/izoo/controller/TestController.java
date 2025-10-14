package com.backend.izoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.izoo.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller para testar configuração de email
 * Este endpoint deve ser REMOVIDO em produção
 */
@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
@Tag(name = "Test", description = "Endpoints de teste - REMOVER EM PRODUÇÃO")
public class TestController {

    @Autowired
    private EmailService emailService;
    
    @Value("${spring.mail.host:NOT_SET}")
    private String smtpHost;
    
    @Value("${spring.mail.port:NOT_SET}")
    private String smtpPort;
    
    @Value("${spring.mail.username:NOT_SET}")
    private String smtpUsername;
    
    @Value("${spring.mail.password:NOT_SET}")
    private String smtpPassword;

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica se a API está funcionando")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "OK",
            "message", "API está funcionando!"
        ));
    }
    
    @GetMapping("/smtp-config")
    @Operation(summary = "Verificar configuração SMTP", description = "Mostra as configurações SMTP (sem mostrar senha completa)")
    public ResponseEntity<?> checkSmtpConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("smtp_host", smtpHost);
        config.put("smtp_port", smtpPort);
        config.put("smtp_username", smtpUsername);
        
        // Mostra apenas se a senha está configurada, não o valor
        if (smtpPassword != null && !smtpPassword.equals("NOT_SET") && !smtpPassword.isEmpty()) {
            config.put("smtp_password", "****" + smtpPassword.substring(Math.max(0, smtpPassword.length() - 4)));
            config.put("password_configured", true);
        } else {
            config.put("smtp_password", "NOT_SET");
            config.put("password_configured", false);
        }
        
        // Verifica se todas as configurações estão OK
        boolean allConfigured = 
            !smtpHost.equals("NOT_SET") && 
            !smtpPort.equals("NOT_SET") && 
            !smtpUsername.equals("NOT_SET") && 
            smtpPassword != null && !smtpPassword.equals("NOT_SET") && !smtpPassword.isEmpty();
        
        config.put("all_configured", allConfigured);
        
        if (!allConfigured) {
            config.put("warning", "Algumas configurações SMTP estão faltando! Adicione as variáveis de ambiente no Render.");
        }
        
        return ResponseEntity.ok(config);
    }

    @PostMapping("/email")
    @Operation(
        summary = "Testar envio de email", 
        description = "Endpoint para testar configuração de email. REMOVER EM PRODUÇÃO!"
    )
    public ResponseEntity<?> testEmail(@RequestBody Map<String, String> request) {
        try {
            String destinatario = request.get("email");
            
            if (destinatario == null || destinatario.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Email é obrigatório"
                ));
            }

            emailService.enviarEmail(
                destinatario,
                "Teste de Email - iZoo",
                "Este é um email de teste da aplicação iZoo.\n\nSe você recebeu este email, a configuração SMTP está funcionando corretamente!"
            );

            return ResponseEntity.ok(Map.of(
                "message", "Email de teste enviado com sucesso para " + destinatario,
                "status", "success"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Erro ao enviar email: " + e.getMessage(),
                "details", e.getClass().getName()
            ));
        }
    }
}
