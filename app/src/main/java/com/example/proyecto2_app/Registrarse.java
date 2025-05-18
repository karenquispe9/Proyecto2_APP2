package com.example.proyecto2_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registrarse extends AppCompatActivity {
    private EditText edtNombreUsuario, edtCorreo, edtContrasena;
    private CheckBox checkBoxTerminos;
    private Button btnSiguiente;
    private TextView txtIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        edtNombreUsuario = findViewById(R.id.NomUser);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContrasena = findViewById(R.id.edtContrasena);
        checkBoxTerminos = findViewById(R.id.terms_checkbox);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        txtIniciarSesion = findViewById(R.id.txtIniciarSesion);

        btnSiguiente.setOnClickListener(v -> {
            String nombreUsuario = edtNombreUsuario.getText().toString().trim();
            String correo = edtCorreo.getText().toString().trim();
            String contrasena = edtContrasena.getText().toString().trim();
            boolean aceptaTerminos = checkBoxTerminos.isChecked();

            if (nombreUsuario.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(Registrarse.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            } else if (!aceptaTerminos) {
                Toast.makeText(Registrarse.this, "Acepte los términos", Toast.LENGTH_SHORT).show();
            } else {
                Usuario usuario = new Usuario(nombreUsuario, correo, contrasena, "personal", "");
                registrarUsuario(usuario);
            }
        });

        txtIniciarSesion.setOnClickListener(v -> {
            startActivity(new Intent(Registrarse.this, IniciarSesion.class));
            finish();
        });
    }

    private void registrarUsuario(Usuario usuario) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<Void> call = apiService.registrarUsuario(usuario);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Registrarse.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Registrarse.this, IniciarSesion.class));
                    finish();
                } else {
                    Toast.makeText(Registrarse.this, "Error en registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Registrarse.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}