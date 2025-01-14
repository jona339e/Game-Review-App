package com.example.gamereview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameCategoryAdapter extends RecyclerView.Adapter<GameCategoryAdapter.ViewHolder> {

    private final List<GameCategory> gameCategories;

    public GameCategoryAdapter(List<GameCategory> gameCategories) {
        this.gameCategories = gameCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameCategory gameCategory = gameCategories.get(position);
        holder.titleTextView.setText(gameCategory.getTitle());
    }

    @Override
    public int getItemCount() {
        return gameCategories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_category_title);
        }
    }
}
