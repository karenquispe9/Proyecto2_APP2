package com.example.proyecto2_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoPost extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 2;

    private CircleImageView profileImage;
    private TextView username;
    private EditText postInput;
    private ImageView subirFoto;
    private Uri imageUri;
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_post);

        // Inicializar vistas
        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        postInput = findViewById(R.id.post_input);
        subirFoto = findViewById(R.id.subirFoto);

        // Inicializar SessionManager
        sessionManager = SessionManager.getInstance(this);

        // Configurar Retrofit
        apiService = RetrofitClient.getApiService();

        // Cargar datos del usuario
        cargarDatosUsuario();

        // Configurar listeners
        subirFoto.setOnClickListener(v -> checkPermissionsAndOpenGallery());

        // Configurar click en la imagen de perfil para volver atrás
        profileImage.setOnClickListener(v -> finish());
    }

    private void cargarDatosUsuario() {
        // 1. Cargar nombre de usuario
        String nombreUsuario = sessionManager.getUserName();
        username.setText(nombreUsuario != null ? nombreUsuario : "Usuario");

        // 2. Configurar opciones para Glide
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image_defecto)  // Mientras carga
                .error(R.drawable.image_defecto);       // Si hay error

        // 3. Obtener URL de la imagen de perfil
        String fotoPerfilUrl = sessionManager.getProfileImageUrl();

        // 4. Cargar imagen con Glide o mostrar imagen por defecto
        if (fotoPerfilUrl != null && !fotoPerfilUrl.isEmpty()) {
            Glide.with(this)
                    .load(fotoPerfilUrl)
                    .apply(requestOptions)
                    .into(profileImage);
        } else {
            profileImage.setImageResource(R.drawable.image_defecto);
        }
    }

    private void checkPermissionsAndOpenGallery() {
        if (PermisosHelper.tienePermisoGaleria(this)) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    PermisosHelper.getPermisosGaleria(),
                    REQUEST_PERMISSION
            );
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permiso necesario para acceder a la galería", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_SHORT).show();
        }
    }

    public void publicarPost(View view) {
        String contenido = postInput.getText().toString().trim();
        int userId = sessionManager.getUserId();

        if (contenido.isEmpty()) {
            Toast.makeText(this, "Escribe algo para publicar", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri != null) {
            subirPostConImagen(userId, contenido, imageUri);
        } else {
            subirPostSinImagen(userId, contenido);
        }
    }

    private void subirPostConImagen(int userId, String contenido, Uri imageUri) {
        try {
            File imageFile = FileUtil.from(this, imageUri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("foto_publicacion", imageFile.getName(), requestFile);

            RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
            RequestBody contenidoBody = RequestBody.create(MediaType.parse("text/plain"), contenido);

            Call<ApiResponse> call = apiService.crearPublicacionConImagen(userIdBody, contenidoBody, imagePart);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(NuevoPost.this, "Publicación creada", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(NuevoPost.this, "Error al publicar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(NuevoPost.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error al procesar imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void subirPostSinImagen(int userId, String contenido) {
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody contenidoBody = RequestBody.create(MediaType.parse("text/plain"), contenido);

        Call<ApiResponse> call = apiService.crearPublicacionSinImagen(userIdBody, contenidoBody);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NuevoPost.this, "Publicación creada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NuevoPost.this, "Error al publicar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(NuevoPost.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void IrAtras(View vista) {
        finish();
    }
}