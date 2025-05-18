package com.example.proyecto2_app;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AgregarMascota extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewFoto;
    private Uri imageUri; // Para guardar la URI de la imagen seleccionada

    private EditText etFechaNacimiento;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mascota);

        // Referencias a los campos de texto
        EditText etTipoMascota = findViewById(R.id.et_tipo_mascota);
        EditText etRaza = findViewById(R.id.et_raza);
        EditText etNombre = findViewById(R.id.et_nombre);
        EditText etGenero = findViewById(R.id.et_genero);
        EditText etTamano = findViewById(R.id.et_tamano);

        etFechaNacimiento = findViewById(R.id.et_fecha_nacimiento);

        // Configurar el listener para abrir el calendario
        etFechaNacimiento.setOnClickListener(v -> showDatePickerDialog());

        // Botones
        Button btnAtras = findViewById(R.id.btn_atras);
        Button btnGuardar = findViewById(R.id.btn_guardar);

        // ImageView para seleccionar foto
        imageViewFoto = findViewById(R.id.iv_anadir_foto);

        // Acción al hacer clic en el ImageView
        imageViewFoto.setOnClickListener(v -> openGallery());

        // Acción Atrás
        btnAtras.setOnClickListener(v -> finish());

        // Acción Guardar
        btnGuardar.setOnClickListener(v -> {
            String tipoMascota = etTipoMascota.getText().toString().trim();
            String raza = etRaza.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();
            String fechaNacimiento = etFechaNacimiento.getText().toString().trim();
            String genero = etGenero.getText().toString().trim();
            String tamano = etTamano.getText().toString().trim();

            // Validación básica
            if (tipoMascota.isEmpty() || raza.isEmpty() || nombre.isEmpty() ||
                    fechaNacimiento.isEmpty() || genero.isEmpty() || tamano.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            String mensaje = "Datos guardados:\nTipo: " + tipoMascota +
                    "\nRaza: " + raza +
                    "\nNombre: " + nombre +
                    "\nFecha nac.: " + fechaNacimiento +
                    "\nGénero: " + genero +
                    "\nTamaño: " + tamano;

            if (imageUri != null) {
                mensaje += "\nFoto seleccionada: " + imageUri.getLastPathSegment();
            } else {
                mensaje += "\nNo se ha seleccionado ninguna foto.";
            }

            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        });
    }

    // Método para abrir la galería
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Recibe el resultado de la galería
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // Guardamos la URI de la imagen
            imageViewFoto.setImageURI(imageUri); // Mostramos la imagen seleccionada
        }
    }

    // Método para mostrar el DatePickerDialog
    private void showDatePickerDialog() {
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    // Formatear mes (los meses empiezan desde 0)
                    String mesStr = String.format("%02d", month1 + 1);
                    String diaStr = String.format("%02d", dayOfMonth);

                    // Mostrar la fecha en formato YYYY-MM-DD
                    String selectedDate = year1 + "-" + mesStr + "-" + diaStr;

                    // Asignar al EditText
                    etFechaNacimiento.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }
}