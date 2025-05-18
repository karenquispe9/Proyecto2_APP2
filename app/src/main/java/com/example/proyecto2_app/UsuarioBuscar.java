package com.example.proyecto2_app;

public class UsuarioBuscar extends Usuario {
    private String nombre;
    private int imageResId;

    public UsuarioBuscar(String nombre, int imageResId) {
        this.nombre = nombre;
        this.imageResId = imageResId;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImageResId() {
        return imageResId;
    }
}