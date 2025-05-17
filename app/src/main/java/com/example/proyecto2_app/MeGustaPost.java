// FavoritosActivity.java
package com.example.proyecto2_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MeGustaPost extends AppCompatActivity {

    private RecyclerView rvPostsFavoritos;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_gusta_post);

        // Inicializar botón de retroceso
        ImageButton btnBack = findViewById(R.id.btnAtras);
        // Inicializar RecyclerView
        rvPostsFavoritos = findViewById(R.id.PostsFavoritos);

        // Acción del botón atrás
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(MeGustaPost.this, ActividadAjustes.class));
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        // Datos de ejemplo para mostrar en el RecyclerView
        List<Post> listaPosts = new ArrayList<>();
        listaPosts.add(new Post(1, "María", "Hermoso paisaje de montaña", R.drawable.scroll3, 150));
        listaPosts.add(new Post(2, "Carlos", "Comida típica del Perú", R.drawable.scroll2, 89));
        listaPosts.add(new Post(3, "Ana", "Playa al atardecer", R.drawable.scroll4, 204));

        // Configurar adaptador y layout manager
        postAdapter = new PostAdapter(listaPosts);
        rvPostsFavoritos.setLayoutManager(new LinearLayoutManager(this));
        rvPostsFavoritos.setAdapter(postAdapter);
    }
}