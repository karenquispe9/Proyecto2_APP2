package com.example.proyecto2_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Perfil extends AppCompatActivity {

    //para el menu de mascotas
    private ConstraintLayout menuMascotas;
    private boolean menuVisible = false;

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private MascotaAdapter mascotaAdapter;

    private List<Post> listaPosts;
    private List<Mascota> listaMascotas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);

        ImageButton btnAjustes = findViewById(R.id.SettingsButton);
        ImageButton btnPerfilMascota = findViewById(R.id.PerfilMascotaButton);
        // Solo busca el botón si menuMascotas no es null y contiene el botón
        menuMascotas = findViewById(R.id.menuMascotas);
        LinearLayout mascotasContainer = findViewById(R.id.mascotasContainer);
        Button btnAgregarMascota = menuMascotas.findViewById(R.id.btnAgregarMascota);
        ImageButton btnCerrarMenu = menuMascotas.findViewById(R.id.btnCerrarMenu);
        // Obtener referencia al BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);

        // Configurar la navegación usando el método reutilizable
        NavegacionBarra.setupBottomNavigationView(bottomNav, this);

        btnAjustes.setOnClickListener(v -> {
            startActivity(new Intent(Perfil.this, Ajustes.class));

        });

        // Mostrar u ocultar menú
        btnPerfilMascota.setOnClickListener(v -> toggleMenu());

        // Acción del botón "Agregar Mascota" --Crear pantalla de agregar mascota
        btnAgregarMascota.setOnClickListener(v -> {
            Intent intent = new Intent(Perfil.this, AgregarMascota.class);
            startActivity(intent);
        });

        btnCerrarMenu.setOnClickListener(v -> toggleMenu());

        // Cargar mascotas estáticas
        String[] nombresMascotas = {"Max", "Luna", "Boby"};
        int[] fotosMascotas = {R.drawable.perfil, R.drawable.perfil, R.drawable.perfil};

        for (int i = 0; i < nombresMascotas.length; i++) {
            View item = getLayoutInflater().inflate(R.layout.item_mascota, mascotasContainer, false);

            TextView nombre = item.findViewById(R.id.nombreMascota);
            ImageView foto = item.findViewById(R.id.fotoMascota);
            ImageButton editar = item.findViewById(R.id.editarMascota);

            nombre.setText(nombresMascotas[i]);
            foto.setImageResource(fotosMascotas[i]);

            final int index = i;
            editar.setOnClickListener(v -> {
                Intent intent = new Intent(Perfil.this, EditarPerfilMascota.class);
                intent.putExtra("mascota_index", index); // o usa ID real si lo tienes
                startActivity(intent);
            });

            mascotasContainer.addView(item);
        }

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPerfil);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Simular datos (esto puedes cambiarlo por datos reales)
        listaPosts = obtenerPostsDelUsuario();
        listaMascotas = obtenerMascotasDelUsuario();

        // Inicializar adaptadores
        postAdapter = new PostAdapter(listaPosts);
        mascotaAdapter = new MascotaAdapter(listaMascotas);

        // Por defecto mostramos posts
        recyclerView.setAdapter(postAdapter);

        // Configurar listeners a los TextView
        TextView tvPublicaciones = findViewById(R.id.Publicaciones);
        TextView tvMascotas = findViewById(R.id.TextMascota);

        tvPublicaciones.setOnClickListener(v -> {
            recyclerView.setAdapter(postAdapter);
        });

        tvMascotas.setOnClickListener(v -> {
            recyclerView.setAdapter(mascotaAdapter);
        });
    }
}