package com.example.proyecto2_app;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.navigation.NavigationBarView;

public class NavegacionBarra {

    public static void setupBottomNavigationView(NavigationBarView bottomNav, Context context) {
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.boton1:
                    if (!(context instanceof nuevoPost)) {
                        context.startActivity(new Intent(context, nuevoPost.class));
                    }
                    return true;
                case R.id.boton2:
                    if (!(context instanceof buscar)) {
                        context.startActivity(new Intent(context, buscar.class));
                    }
                    return true;
                case R.id.boton3:
                    if (!(context instanceof pagina_inicio)) {
                        context.startActivity(new Intent(context, pagina_inicio.class));
                    }
                    return true;
                case R.id.boton4:
                    if (!(context instanceof tienda)) {
                        context.startActivity(new Intent(context, tienda.class));
                    }
                    return true;
                case R.id.boton5:
                    if (!(context instanceof perfil)) {
                        context.startActivity(new Intent(context, perfil.class));
                    }
                    return true;
            }
            return false;
        });
    }
}