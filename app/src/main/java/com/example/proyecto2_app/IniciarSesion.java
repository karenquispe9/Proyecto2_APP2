package com.example.proyecto2_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IniciarSesion extends AppCompatActivity {

    private EditText edtCorreo, edtContrasena;
    private Button btnEntrar;
    private TextView txtRegistrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        // Inicializar vistas
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContrasena = findViewById(R.id.edtContrasena);
        btnEntrar = findViewById(R.id.btnEntrar);
        txtRegistrate = findViewById(R.id.txtRegistrate);

        // Configurar el clic del botón
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos del formulario
                String correo = edtCorreo.getText().toString().trim();
                String contrasena = edtContrasena.getText().toString().trim();

                // Validar que los campos no estén vacíos
                if (correo.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(IniciarSesion.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Iniciar sesión usando Retrofit
                    iniciarSesion(correo, contrasena);
                }
            }
        });

        // Configurar el clic del texto
        txtRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Dirigiendo pagina de registro", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Registrarse.class);
                startActivity(intent);
            }
        });

    }

    private void iniciarSesion(String correo, String contrasena) {
        // Obtener el servicio de la API
        ApiService apiService = RetrofitClient.getApiService();

        // Realizar la solicitud POST
        Call<LoginResponse> call = apiService.iniciarSesion(correo, contrasena);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Si la API responde con éxito
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(IniciarSesion.this, loginResponse.getMensaje(), Toast.LENGTH_SHORT).show();

                    // Redirigir a la siguiente actividad
                    Intent intent = new Intent(IniciarSesion.this, PaginaInicio.class);
                    startActivity(intent);
                } else {
                    // Si la API responde con un error
                    Toast.makeText(IniciarSesion.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Si hay un error en la solicitud
                Toast.makeText(IniciarSesion.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}