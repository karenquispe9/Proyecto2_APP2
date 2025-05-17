package com.example.proyecto2_app;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post> listaPosts;

    public PostAdapter(List<Post> listaPosts) {
        this.listaPosts = listaPosts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = listaPosts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return listaPosts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvAutor, tvDescripcion, tvLikesCount;
        ImageView ivPostImage, ivLike;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            ivLike = itemView.findViewById(R.id.ivLike);
        }

        public void bind(Post post) {
            tvAutor.setText(post.getAutor());
            tvDescripcion.setText(post.getDescripcion());
            tvLikesCount.setText(post.getLikes() + " Me gusta");
            ivPostImage.setImageResource(post.getImagenResId());

            // Simular toggle de like
            ivLike.setTag("outline"); // Estado inicial

            ivLike.setOnClickListener(v -> {
                if ("outline".equals(ivLike.getTag())) {
                    ivLike.setImageResource(R.drawable.ic_heart);
                    ivLike.setTag("filled");
                    int nuevoLike = post.getLikes() + 1;
                    tvLikesCount.setText(nuevoLike + " Me gusta");
                } else {
                    ivLike.setImageResource(R.drawable.ic_heart_outline);
                    ivLike.setTag("outline");
                    int nuevoLike = post.getLikes() - 1;
                    tvLikesCount.setText(nuevoLike + " Me gusta");
                }
            });
        }
    }
}