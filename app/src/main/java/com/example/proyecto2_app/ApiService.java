package com.example.proyecto2_app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    // Método para registrar un usuario
    @POST("usuarios/")
    Call<Void> registrarUsuario(@Body Usuario usuario);

    // Método para iniciar sesión
    @FormUrlEncoded
    @POST("login/")
    Call<LoginResponse> iniciarSesion(
            @Field("correo") String correo,
            @Field("contraseña") String contraseña
    );
}