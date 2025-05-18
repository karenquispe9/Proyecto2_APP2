package com.example.proyecto2_app;

import com.google.gson.annotations.SerializedName;

public class Publicacion {
    @SerializedName("id_publicacion")
    private int idPublicacion;

    @SerializedName("id_usuario")
    private int idUsuario;

    @SerializedName("contenido")
    private String contenido;

    @SerializedName("foto_publicacion")
    private String fotoPublicacion;

    @SerializedName("foto_url")
    private String fotoUrl;

    @SerializedName("nombre_usuario")
    private String nombreUsuario;

    @SerializedName("foto_perfil_url")
    private String fotoPerfilUrl;

    @SerializedName("fecha_publicacion")
    private String fechaPublicacion;

    // Getters y Setters
    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFotoPublicacion() {
        return fotoPublicacion;
    }

    public void setFotoPublicacion(String fotoPublicacion) {
        this.fotoPublicacion = fotoPublicacion;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}