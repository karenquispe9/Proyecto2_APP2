package com.example.proyecto2_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Tienda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tienda);

        // Obtener referencia al BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);

        NavegacionBarra.setupBottomNavigationView(bottomNav, this);

        GridLayout gridProductos = findViewById(R.id.gridProductos);

        String[] nombres = {"Seresto Collar Antiparasitario para perros", "Cunipic Love Birds comida para agapornis", "Anillos Filtro para acuarios Eheim Mech", "Home Friends Special Heno Timothy para roedores"};
        int[] imagenes = {R.drawable.producto1, R.drawable.producto2, R.drawable.producto3, R.drawable.producto4};
        int[] marcas = {R.drawable.marca1, R.drawable.marca2, R.drawable.marca1, R.drawable.marca2};
        String[] urls = {"https://www.tiendanimal.es/seresto-collar-antiparasitario-para-perros/BAY83883961_M.html", "https://www.tiendanimal.es/cunipic-love-birds-comida-para-agapornis/CUNAGA650_M.html", "https://www.kiwoko.com/peces/acuarios-y-peceras/filtros-y-bombas/interior/anillos-filtro-para-acuarios-eheim-mech/EHE2507051_M.html", "https://www.tiendanimal.es/home-friends-special-heno-timothy-para-roedores/HOMCOM301180.html?utm_source=google&utm_medium=cpc&utm_campaign=is_ta_es_ao_ec_pmax_todos-los-productos_mix&utm_id=18020851897&gad_source=1&gad_campaignid=17347750751&gbraid=0AAAAADooYC_J7XQNaX1G6Brjn0m7gyIUL&gclid=Cj0KCQjwiqbBBhCAARIsAJSfZkZzhqxGHcqAi1ftE3-wF0VdDl1zVe4RDz4Kn63HOgG_WRPv_yUOLSMaAmIYEALw_wcB"};

        for (int i = 0; i < nombres.length; i++) {
            final int index = i;

            LinearLayout productoLayout = new LinearLayout(this);
            productoLayout.setOrientation(LinearLayout.VERTICAL);
            productoLayout.setPadding(16, 16, 16, 16);

            ImageView imgProducto = new ImageView(this);
            LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(300, 300); // Tamaño más grande
            imgProducto.setLayoutParams(paramsImg);
            imgProducto.setScaleType(ImageView.ScaleType.CENTER_CROP); // Opcional, para recortar y centrar la imagen
            imgProducto.setImageResource(imagenes[index]);
            imgProducto.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[index]));
                startActivity(browserIntent);
            });

            LinearLayout nombreMarcaLayout = new LinearLayout(this);
            nombreMarcaLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView nombre = new TextView(this);
            nombre.setText(nombres[i]);
            nombre.setTextSize(16);
            nombre.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[index]));
                startActivity(browserIntent);
            });

            // Imagen de marca: asegúrate de que la imagen exista y usa WRAP_CONTENT
            ImageView imgMarca = new ImageView(this);
            imgMarca.setImageResource(marcas[index]);
            LinearLayout.LayoutParams marcaParams = new LinearLayout.LayoutParams(60, 60);
            marcaParams.setMargins(16, 0, 0, 0); // Separación a la izquierda del nombre
            imgMarca.setLayoutParams(marcaParams);

            nombreMarcaLayout.addView(nombre);
            nombreMarcaLayout.addView(imgMarca);

            productoLayout.addView(imgProducto);
            productoLayout.addView(nombreMarcaLayout);



            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setGravity(Gravity.CENTER);
            params.setMargins(0, 0, 0, 32); // Margen inferior para separar filas
            productoLayout.setLayoutParams(params);

            gridProductos.addView(productoLayout);
        }


    }
}