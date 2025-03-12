package com.example.proyecto2_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private HorizontalScrollView miScrollView;
    private final Handler handler = new Handler();
    private int scrollX = 0;
    private boolean  userScrolling = false; // Para detectar si el usuario está deslizando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_iniciar_session = findViewById(R.id.btn_iniciar_sesion );
        Button btn_registrarse = findViewById(R.id. btn_registrarse);
        miScrollView = findViewById(R.id.miScrollView);


        btn_iniciar_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IniciarSesion.class);
                startActivity(intent);
            }
        });

        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registrarse.class);
                startActivity(intent); // Inicia la nueva actividad
            }
        });


        // Verifica que el ScrollView tiene contenido antes de iniciar el auto-scroll
        if (miScrollView.getChildAt(0) != null) {
            iniciarAutoScroll();
        }

        /* Detectar si el usuario está desplazando manualmente
        miScrollView.setOnTouchListener(new View.OnTouchListener() {
            boolean userScrolling = false;
            Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    userScrolling = true;  // Usuario toca el scroll
                    handler.removeCallbacks(autoScrollRunnable); // Detener auto-scroll
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            userScrolling = false;
                            iniciarAutoScroll(); // Reanudar después de 5 segundos sin tocar
                        }
                    }, 5000);
                }
                return false; // Permitir que el evento continúe propagándose
            }
        });*/


    }


    // Runnable para hacer scroll automáticamente
    private final Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (!userScrolling && miScrollView.getChildAt(0) != null) {
                int maxScroll = miScrollView.getChildAt(0).getWidth() - miScrollView.getWidth();
                scrollX += 1000; // Ajusta la cantidad de desplazamiento

                if (scrollX >= maxScroll) {
                    scrollX = 0; // Reiniciar si llega al final
                }

                miScrollView.smoothScrollTo(scrollX, 0);
            }
            handler.postDelayed(this, 5000); // Repetir cada 5 segundos
        }
    };

    private void iniciarAutoScroll() {
        handler.postDelayed(autoScrollRunnable, 5000); // Iniciar después de 5 segundos
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoScrollRunnable); // Evitar memory leaks
    }


}
