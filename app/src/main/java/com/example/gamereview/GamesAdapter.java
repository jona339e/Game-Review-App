package com.example.gamereview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private final List<Game> games;
    public interface OnGameClickListener {
        void onGameClick(Game game);
    }
    private final OnGameClickListener onGameClickListener;
    public GamesAdapter(List<Game> games, OnGameClickListener onGameClickListener) {
        this.games = games;
        this.onGameClickListener = onGameClickListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = games.get(position);

        // Set view elements
        holder.gameNameTextView.setText(game.getName());
        holder.gameRatingTextView.setText("Rating: " + game.getRating());
        String platforms = android.text.TextUtils.join(", ", game.getPlatforms());
        holder.gamePlatformsTextView.setText("Platforms: " + platforms);

        Glide.with(holder.gameImageView.getContext())
                .load(game.getImageUrl())
                .placeholder(R.drawable.baseline_video_game_asset_24)
                .error(R.drawable.baseline_video_game_asset_24)
                .into(holder.gameImageView);

        // Handle click to add to the selectedGames HashSet
        holder.itemView.setOnClickListener(v -> {
            boolean added = User.getInstance().getSelectedGames().add(game);
            if (added) {
                GameManager.getInstance().addGameToUser(game, v.getContext());
                Toast.makeText(v.getContext(), game.getName() + " added to Currently Playing!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(v.getContext(), game.getName() + " is already in Currently Playing!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return games.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameNameTextView;
        TextView gamePlatformsTextView;
        TextView gameRatingTextView;
        ImageView gameImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind the views
            gameNameTextView = itemView.findViewById(R.id.text_game_name);
            gamePlatformsTextView = itemView.findViewById(R.id.text_game_platforms);
            gameRatingTextView = itemView.findViewById(R.id.text_game_rating);
            gameImageView = itemView.findViewById(R.id.cover_art);
        }
    }
}
