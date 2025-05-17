package com.example.proyecto2_app;
import com.google.gson.annotations.SerializedName;


public class Usuario {
    @SerializedName("id_usuario")
    private int id;
    private String nombre;
    private String email;
    private String contraseña;
    private String tipo_usuario;
    private String codigo_postal;

    // añadidos para editarpefil.java
    private String descripcion;
    private String foto_url;

    // Constructor vacío (necesario para Retrofit)
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String nombre, String email, String contraseña, String tipo_usuario, String codigo_postal, String descripcion, String foto_url) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.tipo_usuario = tipo_usuario;
        this.codigo_postal = codigo_postal;
        this.descripcion = descripcion;
        this.foto_url = foto_url;
    }

    public Usuario(String nombreUsuario, String correo, String contrasena, String personal, String s) {
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

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFoto_url() { return foto_url; }
    public void setFoto_url(String foto_url) { this.foto_url = foto_url; }

    public int getIdUsuario() {
        return id;
    }

}