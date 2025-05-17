package com.example.proyecto2_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 123;

    private HorizontalScrollView miScrollView;
    private final Handler handler = new Handler();
    private int scrollX = 0;
    private boolean userScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_iniciar_session = findViewById(R.id.btn_iniciar_sesion);
        Button btn_registrarse = findViewById(R.id.btn_registrarse);
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
                startActivity(intent);
            }
        });

        if (miScrollView.getChildAt(0) != null) {
            iniciarAutoScroll();
        }

        //comprovar permis per accedir a la galeria
        checkPhotoPermissionOnce();
    }

    private final Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (!userScrolling && miScrollView.getChildAt(0) != null) {
                int maxScroll = miScrollView.getChildAt(0).getWidth() - miScrollView.getWidth();
                scrollX += 995;

                if (scrollX >= maxScroll) {
                    scrollX = 0;
                }

                miScrollView.smoothScrollTo(scrollX, 0);
            }
            handler.postDelayed(this, 4000);
        }
    };

    private void iniciarAutoScroll() {
        handler.postDelayed(autoScrollRunnable, 1500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoScrollRunnable);
    }

    // ✅ FUNCIONS DE PERMÍS
    private void checkPhotoPermissionOnce() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+: nou permís
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        REQUEST_CODE_STORAGE_PERMISSION);
            }
        } else {
            // Android < 13
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_STORAGE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permís per fotos concedit!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "⚠️ Necessites donar permís per accedir a les fotos.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
