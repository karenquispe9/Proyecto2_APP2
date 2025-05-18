package com.example.proyecto2_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaginaInicio extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PublicacionAdapter adapter;
    private List<Publicacion> publicaciones = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_inicio);

        // Configurar navegación inferior
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        NavegacionBarra.setupBottomNavigationView(bottomNav, this);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPublicaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PublicacionAdapter(this, publicaciones);
        recyclerView.setAdapter(adapter);

        // Configurar SwipeRefresh
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::cargarPublicaciones);

        // Cargar publicaciones iniciales
        cargarPublicaciones();
    }

    private void cargarPublicaciones() {
        swipeRefreshLayout.setRefreshing(true);

        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Publicacion>> call = apiService.obtenerTodasPublicaciones();

        call.enqueue(new Callback<List<Publicacion>>() {
            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    publicaciones.clear();
                    publicaciones.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(PaginaInicio.this, "Error al cargar publicaciones", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(PaginaInicio.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("PaginaInicio", "Error: " + t.getMessage());
            }
        });
    }
}