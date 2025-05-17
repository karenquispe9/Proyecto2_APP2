package com.example.proyecto2_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);

        ImageButton btnAjustes = findViewById(R.id.SettingsButton);

        // Obtener referencia al BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);

        NavegacionBarra.setupBottomNavigationView(bottomNav, this);

        btnAjustes.setOnClickListener(v -> {
            startActivity(new Intent(Perfil.this, Ajustes.class));

        });
    }
}