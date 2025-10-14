package com.backend.izoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

/**
 * Serviço para envio de emails
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@izoo.com}")
    private String remetenteEmail;
    
    @Value("${spring.mail.host:smtp.gmail.com}")
    private String smtpHost;
    
    @Value("${spring.mail.port:465}")
    private String smtpPort;
    
    @PostConstruct
    public void init() {
        System.out.println("=== CONFIGURAÇÃO DE EMAIL ===");
        System.out.println("SMTP Host: " + smtpHost);
        System.out.println("SMTP Port: " + smtpPort);
        System.out.println("Email Remetente: " + remetenteEmail);
        System.out.println("============================");
    }

    /**
     * Envia email de recuperação de senha com código
     * 
     * @param destinatario Email do destinatário
     * @param login Login do usuário
     * @param codigo Código de recuperação de 6 dígitos
     */
    public void enviarEmailRecuperacaoSenha(String destinatario, String login, String codigo) {
        try {
            System.out.println(">>> Iniciando envio de email de recuperação");
            System.out.println(">>> De: " + remetenteEmail);
            System.out.println(">>> Para: " + destinatario);
            System.out.println(">>> Assunto: iZoo - Recuperação de Senha");
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetenteEmail);
            message.setTo(destinatario);
            message.setSubject("iZoo - Recuperação de Senha");
            message.setText(construirCorpoEmail(login, codigo));
            
            System.out.println(">>> Tentando enviar email via SMTP " + smtpHost + ":" + smtpPort);
            
            long startTime = System.currentTimeMillis();
            mailSender.send(message);
            long endTime = System.currentTimeMillis();
            
            System.out.println(">>> ✅ Email enviado com sucesso em " + (endTime - startTime) + "ms");
        } catch (org.springframework.mail.MailAuthenticationException e) {
            System.err.println(">>> ❌ ERRO DE AUTENTICAÇÃO SMTP");
            System.err.println(">>> Verifique SMTP_USERNAME e SMTP_PASSWORD no Render");
            System.err.println(">>> Detalhes: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha na autenticação SMTP. Verifique as credenciais.", e);
        } catch (org.springframework.mail.MailSendException e) {
            System.err.println(">>> ❌ ERRO AO ENVIAR EMAIL");
            System.err.println(">>> Detalhes: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao enviar email: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println(">>> ❌ ERRO DESCONHECIDO AO ENVIAR EMAIL");
            System.err.println(">>> Tipo: " + e.getClass().getName());
            System.err.println(">>> Mensagem: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro inesperado ao enviar email: " + e.getMessage(), e);
        }
    }

    /**
     * Constrói o corpo do email com o código de recuperação
     */
    private String construirCorpoEmail(String login, String codigo) {
        return """
                Olá %s, tudo bem?
                
                O código de recuperação é: %s, e é válido por 15 minutos!
                
                Se você não solicitou esta recuperação, ignore este email.
                
                Atenciosamente,
                Equipe iZoo
                """.formatted(login, codigo);
    }

    /**
     * Envia email genérico
     * 
     * @param destinatario Email do destinatário
     * @param assunto Assunto do email
     * @param corpo Corpo do email
     */
    public void enviarEmail(String destinatario, String assunto, String corpo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetenteEmail);
            message.setTo(destinatario);
            message.setSubject(assunto);
            message.setText(corpo);
            
            mailSender.send(message);
            
            System.out.println("Email enviado para: " + destinatario);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar email", e);
        }
    }
}
