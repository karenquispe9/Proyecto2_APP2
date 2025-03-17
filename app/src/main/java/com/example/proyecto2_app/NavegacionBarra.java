package com.example.proyecto2_app;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.navigation.NavigationBarView;

public class NavegacionBarra {

    public static void setupBottomNavigationView(NavigationBarView bottomNav, Context context) {
        if (bottomNav == null) {
            return; // Si no hay BottomNavigationView, no hacer nada
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId(); // Obtener el ID del ítem seleccionado

            if (id == R.id.boton1) {
                if (!(context instanceof NuevoPost)) {
                    context.startActivity(new Intent(context, NuevoPost.class));
                }
                return true;
            } else if (id == R.id.boton2) {
                if (!(context instanceof Buscar)) {
                    context.startActivity(new Intent(context, Buscar.class));
                }
                return true;
            } else if (id == R.id.boton3) {
                if (!(context instanceof PaginaInicio)) {
                    context.startActivity(new Intent(context, PaginaInicio.class));
                }
                return true;
            } else if (id == R.id.boton4) {
                if (!(context instanceof Tienda)) {
                    context.startActivity(new Intent(context, Tienda.class));
                }
                return true;
            } else if (id == R.id.boton5) {
                if (!(context instanceof Perfil)) {
                    context.startActivity(new Intent(context, Perfil.class));
                }
                return true;
            }

            return false; // Si no se selecciona ningún ítem válido
        });
    }
}