package com.backend.izoo.dto;

public class LoginResponseDTO {
    
    private String message;
    private String token;
    private String tipo = "Bearer";
    private UsuarioDTO usuario;
    
    public LoginResponseDTO() {}
    
    public LoginResponseDTO(String message, String token, UsuarioDTO usuario) {
        this.message = message;
        this.token = token;
        this.usuario = usuario;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public UsuarioDTO getUsuario() {
        return usuario;
    }
    
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}