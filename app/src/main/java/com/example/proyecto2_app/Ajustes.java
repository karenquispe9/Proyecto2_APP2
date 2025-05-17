package com.example.proyecto2_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Ajustes extends AppCompatActivity {
    LinearLayout rowConfigCuenta, rowActividad, rowEditarPerfil;
    Button bnCerrarSession;
    ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajustes);

        rowConfigCuenta = findViewById(R.id.rowConfigCuenta);
        rowActividad = findViewById(R.id.rowActividad);
        rowEditarPerfil = findViewById(R.id.rowEditarPerfil);
        bnCerrarSession = findViewById(R.id.bnCerrarSession);
        btnAtras = findViewById(R.id.AtrasButton);


        rowConfigCuenta.setOnClickListener(v -> {
            startActivity(new Intent(Ajustes.this, ConfiguracionCuenta.class));
        });

        rowActividad.setOnClickListener(v -> {
            startActivity(new Intent(Ajustes.this, ActividadAjustes.class));
        });

        rowEditarPerfil.setOnClickListener(v -> {
            startActivity(new Intent(Ajustes.this, EditarPerfil.class));
        });

        bnCerrarSession.setOnClickListener(v -> {
            startActivity(new Intent(Ajustes.this, IniciarSesion.class));
        });

        btnAtras.setOnClickListener(v -> {
            startActivity(new Intent(Ajustes.this, Perfil.class));
        });



    }
}