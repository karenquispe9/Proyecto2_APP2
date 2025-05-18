package com.example.proyecto2_app;

import android.net.Uri;

public class Mascota {
    private String nombre;
    private String tipo;
    private String raza;
    private String fechaNacimiento;
    private String genero;
    private String tamaño;
    private Uri fotoUri;

    public Mascota(String nombre, String tipo, String raza, String fechaNacimiento, String genero, String tamaño, Uri fotoUri) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.tamaño = tamaño;
        this.fotoUri = fotoUri;
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getRaza() { return raza; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getGenero() { return genero; }
    public String getTamaño() { return tamaño; }
    public Uri getFotoUri() { return fotoUri; }
}