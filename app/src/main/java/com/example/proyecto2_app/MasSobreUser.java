package com.example.proyecto2_app;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;


public class MasSobreUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_sobre_user);

        AutoCompleteTextView dropdown = findViewById(R.id.dropdownOpciones);

        //lista de opciones
        String[] opciones = {"Normal", "Protectoras", "Asociación", "Empresa"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, opciones);
        dropdown.setAdapter(adapter);

        dropdown.setKeyListener(null);
        dropdown.setCursorVisible(false);
        dropdown.setFocusable(false);//para q no pueda escribir el usuario
        dropdown.setFocusableInTouchMode(false);

        //aqui se maneja la seleccion del usuario
        dropdown.setOnItemClickListener((parent, view, position, id) -> {
            String seleccion = opciones[position];
            Toast.makeText(this, "Has seleccionado: "+seleccion, Toast.LENGTH_SHORT).show();

            dropdown.setHint("");
        });



    }
}