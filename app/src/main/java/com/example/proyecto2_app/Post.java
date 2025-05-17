package com.example.proyecto2_app;


public class Post {
    private int id;
    private String autor;
    private String descripcion;
    private int imagenResId; // ID del recurso drawable
    private int likes;

    public Post(int id, String autor, String descripcion, int imagenResId, int likes) {
        this.id = id;
        this.autor = autor;
        this.descripcion = descripcion;
        this.imagenResId = imagenResId;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public String getAutor() {
        return autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getImagenResId() {
        return imagenResId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
