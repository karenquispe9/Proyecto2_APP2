package com.example.proyecto2_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActividadAjustes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actividad_ajustes);

        ImageView btnPostLikes = findViewById(R.id.btnPostLikes);
        ImageView btnBack = findViewById(R.id.AtrasButton);

        btnPostLikes.setOnClickListener(v -> {
            startActivity(new Intent(ActividadAjustes.this, MeGustaPost.class));
        });

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(ActividadAjustes.this, Ajustes.class));
        });
    }
}