package com.example.proyecto2_app;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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


    @PUT("usuarios/actualizar-perfil/{id_usuario}")
    Call<Void> actualizarPerfil(
            @Path("id_usuario") int idUsuario,
            @Query("nombre") String nombre,
            @Query("descripcion") String descripcion,
            @Part MultipartBody.Part foto_usuario
    );

}