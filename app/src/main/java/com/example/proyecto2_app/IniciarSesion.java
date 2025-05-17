package com.example.proyecto2_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
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
    private CheckBox cbMantenerSesion;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        sessionManager = SessionManager.getInstance(this);

        //verificar si ya esta loegado
        if (sessionManager.isLoggedIn()) {
            irAPaginaInicio();
            return;
        }

        edtCorreo = findViewById(R.id.edtCorreo);
        edtContrasena = findViewById(R.id.edtContrasena);
        cbMantenerSesion = findViewById(R.id.cbMantenerSesion);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        txtRegistrate = findViewById(R.id.txtRegistrate);

        //configurar chek box
        cbMantenerSesion.setChecked(sessionManager.shouldRememberMe());

        btnEntrar.setOnClickListener(v -> {
            String correo = edtCorreo.getText().toString().trim();
            String contrasena = edtContrasena.getText().toString().trim();

            if (!validarCampos(correo, contrasena)) {
                return;
            }

            iniciarSesion(correo, contrasena, cbMantenerSesion.isChecked());
        });

        findViewById(R.id.txtRegistrate).setOnClickListener(v -> {
            startActivity(new Intent(this, Registrarse.class));
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

    private void iniciarSesion(String correo, String contrasena, boolean recordarSesion) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<LoginResponse> call = apiService.iniciarSesion(correo, contrasena);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.getMensaje().equals("Inicio de sesión exitoso")) {
                        // Guardar datos del usuario en la sesión
                        Usuario usuario = loginResponse.getUsuario();
                        if (usuario != null) {
                            sessionManager.createLoginSession(
                                    usuario.getIdUsuario(),
                                    usuario.getNombre(),
                                    usuario.getEmail(),
                                    usuario.getTipoUsuario(),
                                    recordarSesion
                            );
                        }

                        irAPaginaInicio();
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

    private void irAPaginaInicio() {
        Intent intent = new Intent(IniciarSesion.this, PaginaInicio.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void mostrarError(String mensaje) {
        runOnUiThread(() -> Toast.makeText(IniciarSesion.this, mensaje, Toast.LENGTH_LONG).show());
    }
}