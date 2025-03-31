package com.example.proyecto2_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.os.Looper;
import java.util.logging.LogRecord;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_TIME = 1000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish(); // <- Asegúrate de que esto esté aquí para cerrar el Splash
            }
        }, SPLASH_TIME);
    }
}