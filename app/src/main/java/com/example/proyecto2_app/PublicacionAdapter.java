package com.example.proyecto2_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.PublicacionViewHolder> {

    private Context context;
    private List<Publicacion> publicaciones;
    private static final String TAG = "PublicacionAdapter";
    private static final String BASE_URL = "https://44.213.214.62";
    private static final OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public PublicacionAdapter(Context context, List<Publicacion> publicaciones) {
        this.context = context;
        this.publicaciones = publicaciones;
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_publicacion, parent, false);
        return new PublicacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionViewHolder holder, int position) {
        Publicacion publicacion = publicaciones.get(position);

        // Configuración de texto
        holder.txtNombreUsuario.setText(publicacion.getNombreUsuario());
        holder.txtContenido.setText(publicacion.getContenido());
        holder.txtFecha.setText(formatearFechaHora(publicacion.getFechaPublicacion()));

        // Cargar foto de perfil
        cargarImagenConGlide(publicacion.getFotoPerfilUrl(), holder.imgPerfil, R.drawable.image_defecto);

        // Cargar imagen de publicación
        if (publicacion.getFotoPublicacion() != null && !publicacion.getFotoPublicacion().isEmpty()) {
            String imageUrl = BASE_URL + "/static/posts/" + publicacion.getFotoPublicacion();
            Log.d("ImageLoad", "Cargando imagen desde: " + imageUrl);
            holder.imgPublicacion.setVisibility(View.VISIBLE);
            cargarImagenConGlide(imageUrl, holder.imgPublicacion, R.drawable.image_defecto);
        } else {
            holder.imgPublicacion.setVisibility(View.GONE);
        }
    }

    private String formatearFechaHora(String fechaOriginal) {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Date date = parser.parse(fechaOriginal);
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy 'a las' HH:mm", Locale.getDefault());
            return formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "Error al formatear fecha: " + fechaOriginal, e);
            return fechaOriginal;
        }
    }

    private void cargarImagenConGlide(String url, ImageView imageView, int placeholder) {
        Log.d("Glide", "Cargando imagen desde: " + url);

        if (url == null || url.isEmpty()) {
            imageView.setImageResource(placeholder);
            return;
        }

        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cachear imágenes
                .placeholder(placeholder) // Imagen mientras carga
                .error(placeholder) // Imagen si hay error
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide", "Error al cargar imagen: " + (e != null ? e.getMessage() : ""));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        Log.d("Glide", "Imagen cargada con éxito");
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return publicaciones != null ? publicaciones.size() : 0;
    }

    public void actualizarDatos(List<Publicacion> nuevasPublicaciones) {
        publicaciones = nuevasPublicaciones;
        notifyDataSetChanged();
    }

    public static class PublicacionViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPerfil, imgPublicacion;
        TextView txtNombreUsuario, txtContenido, txtFecha;

        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPerfil = itemView.findViewById(R.id.imgPerfil);
            imgPublicacion = itemView.findViewById(R.id.imgPublicacion);
            txtNombreUsuario = itemView.findViewById(R.id.txtNombreUsuario);
            txtContenido = itemView.findViewById(R.id.txtContenido);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }
}