package com.example.proyecto2_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;

public class IniciarSesion extends AppCompatActivity {

    private EditText edtCorreo, edtContrasena;
    private TextView txtRegistrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        edtCorreo = findViewById(R.id.edtCorreo);
        edtContrasena = findViewById(R.id.edtContrasena);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        txtRegistrate = findViewById(R.id.txtRegistrate);

        btnEntrar.setOnClickListener(v -> {
            String correo = edtCorreo.getText().toString().trim();
            String contrasena = edtContrasena.getText().toString().trim();

            if (!validarCampos(correo, contrasena)) {
                return;
            }

            iniciarSesion(correo, contrasena);
        });


        txtRegistrate.setOnClickListener(v -> {
            startActivity(new Intent(IniciarSesion.this, Registrarse.class));
            finish();
        });
    }

    private boolean validarCampos(String correo, String contrasena) {
        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            edtCorreo.setError("Ingrese un correo electrónico válido");
            edtCorreo.requestFocus();
            return false;
        }

        if (contrasena.length() < 6) {
            edtContrasena.setError("La contraseña debe tener al menos 6 caracteres");
            edtContrasena.requestFocus();
            return false;
        }

        return true;
    }

    private void iniciarSesion(String correo, String contrasena) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<LoginResponse> call = apiService.iniciarSesion(correo, contrasena);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.getMensaje().equals("Inicio de sesión exitoso")) {
                        startActivity(new Intent(IniciarSesion.this, PaginaInicio.class));
                        finish();
                    } else {
                        mostrarError("Credenciales incorrectas");
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        mostrarError("Error: " + errorBody);
                        Log.e("API_ERROR", "Código: " + response.code() + " - " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mostrarError("Error de conexión: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void mostrarError(String mensaje) {
        runOnUiThread(() -> Toast.makeText(IniciarSesion.this, mensaje, Toast.LENGTH_LONG).show());
    }
}