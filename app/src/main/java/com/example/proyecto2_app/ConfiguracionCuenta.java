package com.example.proyecto2_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ConfiguracionCuenta extends AppCompatActivity {

    private Switch switchModoOscuro;
    //   private Switch switchModoPrivado;
    private TextView textViewIdiomaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuracion_cuenta);

        switchModoOscuro = findViewById(R.id.switchModoOscuro);
        textViewIdiomaSeleccionado = findViewById(R.id.textViewIdiomaSeleccionado);

        // Botón atrás
        ImageButton atrasButton = findViewById(R.id.AtrasButton);
        atrasButton.setOnClickListener(v -> finish());

        // Modo oscuro
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switchModoOscuro.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);

        switchModoOscuro.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Fila de idioma - Click listener
        findViewById(R.id.row2).setOnClickListener(v -> mostrarSelectorDeIdioma());

        // Mostrar idioma actual
        String idiomaActual = Locale.getDefault().getLanguage();
        actualizarTextoIdioma(idiomaActual);
    }

    private void mostrarSelectorDeIdioma() {
        final String[] idiomas = {"Español", "English"};
        final String[] codigos = {"es", "en"};

        new AlertDialog.Builder(this)
                .setTitle("Selecciona un idioma")
                .setItems(idiomas, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idiomaSeleccionado = codigos[which];
                        cambiarIdioma(idiomaSeleccionado);
                        textViewIdiomaSeleccionado.setText(idiomas[which]);
                    }
                })
                .show();
    }

    private void cambiarIdioma(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        // Reiniciar la actividad para aplicar el cambio
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void actualizarTextoIdioma(String langCode) {
        if (langCode.equals("es")) {
            textViewIdiomaSeleccionado.setText("Español");
        } else if (langCode.equals("en")) {
            textViewIdiomaSeleccionado.setText("English");
        }
    }
}