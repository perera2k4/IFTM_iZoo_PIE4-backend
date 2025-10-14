package com.backend.izoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Serviço para envio de emails
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@izoo.com}")
    private String remetenteEmail;

    /**
     * Envia email de recuperação de senha com código
     * 
     * @param destinatario Email do destinatário
     * @param codigo Código de recuperação de 6 dígitos
     */
    public void enviarEmailRecuperacaoSenha(String destinatario, String codigo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetenteEmail);
            message.setTo(destinatario);
            message.setSubject("iZoo - Recuperação de Senha");
            message.setText(construirCorpoEmail(codigo));
            
            mailSender.send(message);
            
            System.out.println("Email de recuperação enviado para: " + destinatario);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar email de recuperação", e);
        }
    }

    /**
     * Constrói o corpo do email com o código de recuperação
     */
    private String construirCorpoEmail(String codigo) {
        return """
                Olá tudo bem?
                
                O código de recuperação é: %s, e é válido por 15 minutos!
                
                Se você não solicitou esta recuperação, ignore este email.
                
                Atenciosamente,
                Equipe iZoo
                """.formatted(codigo);
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
