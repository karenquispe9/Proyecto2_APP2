package com.example.proyecto2_app;

public class Usuario {
    private String nombre;
    private String email;
    private String contraseña;
    private String tipo_usuario;
    private String codigo_postal;

    // Constructor vacío (necesario para Retrofit)
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String nombre, String email, String contraseña, String tipo_usuario, String codigo_postal) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.tipo_usuario = tipo_usuario;
        this.codigo_postal = codigo_postal;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipoUsuario() {
        return tipo_usuario;
    }

    public void setTipoUsuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getCodigoPostal() {
        return codigo_postal;
    }

    public void setCodigoPostal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }
}