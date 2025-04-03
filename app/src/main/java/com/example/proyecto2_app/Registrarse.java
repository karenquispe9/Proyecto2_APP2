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
    //eliminar
    private Button btnSaberMas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        // Inicializar vistas
        edtNombreUsuario = findViewById(R.id.NomUser);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContrasena = findViewById(R.id.edtContrasena);
        checkBoxTerminos = findViewById(R.id.terms_checkbox);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        txtIniciarSesion = findViewById(R.id.txtIniciarSesion);

        btnSaberMas = findViewById(R.id.btnSaberMas);

        btnSaberMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MasSobreUser.class);
                startActivity(intent);
            }
        });

        // Configurar el clic del botón
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos del formulario
                String nombreUsuario = edtNombreUsuario.getText().toString().trim();
                String correo = edtCorreo.getText().toString().trim();
                String contrasena = edtContrasena.getText().toString().trim();
                boolean aceptaTerminos = checkBoxTerminos.isChecked();

                // Validar que los campos no estén vacíos y que se acepten los términos
                if (nombreUsuario.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(Registrarse.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else if (!aceptaTerminos) {
                    Toast.makeText(Registrarse.this, "Debe aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
                } else {
                    // Crear un objeto Usuario con los datos del formulario
                    Usuario usuario = new Usuario(nombreUsuario, correo, contrasena, "personal", "");

                    // Registrar al usuario usando Retrofit
                    registrarUsuario(usuario);
                }
            }
        });

        txtIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Dirigiendo pagina de Inicio Sesion", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), IniciarSesion.class);
                startActivity(intent);
            }
        });
    }

    private void registrarUsuario(Usuario usuario) {
        // Obtener el servicio de la API
        ApiService apiService = RetrofitClient.getApiService();

        // Realizar la solicitud POST
        Call<Void> call = apiService.registrarUsuario(usuario);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la API responde con éxito
                    Toast.makeText(Registrarse.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                    // Redirigir a la siguiente actividad
                    Intent intent = new Intent(Registrarse.this, PaginaInicio.class);
                    startActivity(intent);
                } else {
                    // Si la API responde con un error
                    Toast.makeText(Registrarse.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hay un error en la solicitud
                Toast.makeText(Registrarse.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}