package com.example.proyecto2_app;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private List<Mascota> listaMascotas;

    public MascotaAdapter(List<Mascota> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascota_perfil, parent, false);
        return new MascotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder holder, int position) {
        Mascota mascota = listaMascotas.get(position);
        // Mostrar nombre
        holder.tvNombre.setText(mascota.getNombre());

        // Mostrar tipo y raza
        holder.tvTipoRaza.setText(mascota.getTipo() + " - " + mascota.getRaza());

        // Cargar imagen con Glide
        if (mascota.getFotoUri() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(mascota.getFotoUri())
                    .into(holder.ivFoto);
        } else {
            holder.ivFoto.setImageResource(R.drawable.logo_petmatch); // Imagen por defecto
        }
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    static class MascotaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvNombre, tvTipoRaza;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.iv_foto_mascota);
            tvNombre = itemView.findViewById(R.id.tv_nombre);
            tvTipoRaza = itemView.findViewById(R.id.tv_tipo_raza);
        }
    }

    // Metodo para actualizar la lista
    public void submitList(List<Mascota> nuevaLista) {
        listaMascotas.clear();
        listaMascotas.addAll(nuevaLista);
        notifyDataSetChanged();
    }
}