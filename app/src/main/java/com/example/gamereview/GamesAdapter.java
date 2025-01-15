package com.example.gamereview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private final List<Game> games;

    public GamesAdapter(List<Game> games) {
        this.games = games;
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
        holder.gameNameTextView.setText(game.getName());
        holder.gameRatingTextView.setText("Rating: " + game.getRating());

        // Format platforms as a comma-separated string
        String platforms = String.join(", ", game.getPlatforms());
        holder.gamePlatformsTextView.setText("Platforms: " + platforms);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameNameTextView;
        TextView gamePlatformsTextView;
        TextView gameRatingTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind the views
            gameNameTextView = itemView.findViewById(R.id.text_game_name);
            gamePlatformsTextView = itemView.findViewById(R.id.text_game_platforms);
            gameRatingTextView = itemView.findViewById(R.id.text_game_rating);
        }
    }
}
