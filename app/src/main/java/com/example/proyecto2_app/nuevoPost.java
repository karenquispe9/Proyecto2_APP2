package com.example.proyecto2_app;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class nuevoPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_post);

        // Obtener referencia al BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);

        // Configurar la navegación usando el método reutilizable
        NavegacionBarra.setupBottomNavigationView(bottomNav, this);

    }

    public void IrAtras(View vista) {
        finish();
    }
}