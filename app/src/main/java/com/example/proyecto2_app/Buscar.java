package com.example.proyecto2_app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Buscar extends AppCompatActivity {

    private EditText editTextSearch;
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private List<Usuario> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);

        // Configurar RecyclerView
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();

        // Agregar algunos usuarios de ejemplo
        userList.add(new UsuarioBuscar("valentina2001", R.drawable.user1));
        userList.add(new UsuarioBuscar("_ana08_", R.drawable.user2));
        userList.add(new UsuarioBuscar("carmen_658", R.drawable.user3));

        userAdapter = new UserAdapter(this, userList);
        recyclerViewUsers.setAdapter(userAdapter);

        // Filtro de búsqueda
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filter(String text) {
        List<Usuario> filteredList = new ArrayList<>();
        for (Usuario u : userList) {
            if (u.getNombre().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(u);
            }
        }
        userAdapter = new UserAdapter(this, filteredList);
        recyclerViewUsers.setAdapter(userAdapter);
    }
}