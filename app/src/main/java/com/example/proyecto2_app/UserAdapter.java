package com.example.proyecto2_app;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<Usuario> userList;
    private Context context;

    public UserAdapter(Context context, List<Usuario> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Usuario user = userList.get(position);

        // Mostrar nombre
        holder.textUserName.setText(user.getNombre());

        // Cargar imagen local (placeholder) o remota con Glide si lo usas
        holder.imageUser.setImageResource(R.drawable.ic_user_placeholder);

        // Acción al hacer clic
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Perfil.class);
            intent.putExtra("usuario_id", user.getIdUsuario());
            intent.putExtra("usuario_nombre", user.getNombre());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView imageUser;
        TextView textUserName;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUser = itemView.findViewById(R.id.imageUser);
            textUserName = itemView.findViewById(R.id.textUserName);
        }
    }
}