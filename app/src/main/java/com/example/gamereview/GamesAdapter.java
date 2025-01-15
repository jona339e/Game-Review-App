package com.example.gamereview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {

    private List<Game> gamesList;

    public GamesAdapter(List<Game> gamesList) {
        this.gamesList = gamesList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        // Get the current game
        Game game = gamesList.get(position);

        // Set the game name
        holder.nameTextView.setText("Name: " + game.getName());

        // Build the platform string from the list of platforms (ensure it's not null)
        StringBuilder platformsText = new StringBuilder();
        if (game.getPlatforms() != null && !game.getPlatforms().isEmpty()) {
            for (Game.Platform platform : game.getPlatforms()) {
                if (platformsText.length() > 0) {
                    platformsText.append(", ");
                }
                platformsText.append(platform.getName());
            }
        } else {
            platformsText.append("No platforms available");
        }
        holder.platformsTextView.setText("Platforms: " + platformsText.toString());

        // Set the game rating
        holder.ratingTextView.setText("Rating: " + game.getRating());
    }


    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, platformsTextView, ratingTextView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_game_name);
            platformsTextView = itemView.findViewById(R.id.text_game_platforms);
            ratingTextView = itemView.findViewById(R.id.text_game_rating);
        }
    }
}
