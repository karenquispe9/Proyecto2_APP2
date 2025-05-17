package com.example.proyecto2_app;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditarPerfil extends AppCompatActivity {

    // Constante para identificar la solicitud de seleccionar una imagen
    private static final int PICK_IMAGE = 1;

    private ImageView ivFotoPerfil;
    private EditText etNombre, etDescripcion;
    private Uri imageUri;  // Guarda la ubicación de la imagen seleccionada


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        ivFotoPerfil = findViewById(R.id.ivFotoPerfil);
        etNombre = findViewById(R.id.etNombre);

        etDescripcion = findViewById(R.id.etDescripcion);
        Button btnCambiarFoto = findViewById(R.id.btnCambiarFoto);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        // Seleccionar foto de galería
        btnCambiarFoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        // Guardar datos
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor completa los campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Suponemos que ya tienes el ID del usuario logueado
            int idUsuario = 1; // ← Aquí deberías tener el ID real del usuario

            // Convertir datos a RequestBody (para usar con @Part)
            RequestBody reqNombre = RequestBody.create(MediaType.parse("text/plain"), nombre);
            RequestBody reqDescripcion = RequestBody.create(MediaType.parse("text/plain"), descripcion);

            // Si hay una imagen seleccionada, convertirla
            MultipartBody.Part reqFoto = null;
            if (imageUri != null) {
                File file = new File(getRealPathFromURI(imageUri));
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                reqFoto = MultipartBody.Part.createFormData("foto_usuario", file.getName(), requestFile);
            }

            // Llamar al API
            ApiService apiService = RetrofitClient.getApiService();

            Call<Void> call = apiService.actualizarPerfil(idUsuario, reqNombre.toString(), reqDescripcion.toString(), reqFoto);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EditarPerfil.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                        finish(); // Volver atrás
                    } else {
                        Toast.makeText(EditarPerfil.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                    Toast.makeText(EditarPerfil.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        });
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    // Este metodo recibe el resultado de la galería cuando el usuario selecciona una imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ivFotoPerfil.setImageURI(imageUri);
        }
    }
}