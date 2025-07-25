package com.example.proyecto2_app;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("usuarios/")
    Call<Void> registrarUsuario(@Body Usuario usuario);

    @GET("login/")
    Call<LoginResponse> iniciarSesion(
            @Query("correo") String correo,
            @Query("contraseña") String contraseña
    );

    @Multipart
    @POST("publicaciones/")
    Call<ApiResponse> crearPublicacionConImagen(
            @Part("id_usuario") RequestBody userId,
            @Part("contenido") RequestBody contenido,
            @Part MultipartBody.Part foto_publicacion
    );

    @Multipart
    @POST("publicaciones/")
    Call<ApiResponse> crearPublicacionSinImagen(
            @Part("id_usuario") RequestBody userId,
            @Part("contenido") RequestBody contenido
    );

    @GET("publicaciones/")
    Call<List<Publicacion>> obtenerTodasPublicaciones();

    @GET("publicaciones/usuario/{id_usuario}")
    Call<List<Publicacion>> obtenerPublicacionesUsuario(@Path("id_usuario") int idUsuario);
}
