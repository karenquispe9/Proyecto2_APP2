package com.example.proyecto2_app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfiguracionCuenta extends AppCompatActivity {

    private Switch switchModoOscuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuracion_cuenta);

        switchModoOscuro = findViewById(R.id.switchModoOscuro);

        // Detectar si ya está en modo oscuro
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switchModoOscuro.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);

        switchModoOscuro.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Botón atrás
        ImageButton atrasButton = findViewById(R.id.AtrasButton);
        atrasButton.setOnClickListener(v -> finish());

    }

}