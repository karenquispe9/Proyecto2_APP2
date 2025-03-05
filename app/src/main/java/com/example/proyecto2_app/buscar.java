package com.example.proyecto2_app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class buscar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);

        // Obtener referencia al BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);

        // Configurar la navegación usando el método reutilizable
        NavegacionBarra.setupBottomNavigationView(bottomNav, this);

    }
}