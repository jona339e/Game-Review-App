package com.example.gamereview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {
    private List<Game> gameList;

    public GamesAdapter(List<Game> gameList) {
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.nameTextView.setText(game.getName());
        holder.ratingTextView.setText(String.valueOf(game.getRating()));

        if (game.getPlatforms() != null) {
            StringBuilder platforms = new StringBuilder();
            for (Platform platform : game.getPlatforms()) {
                platforms.append(platform.getName()).append(", ");
            }
            holder.platformsTextView.setText(platforms.toString());
        }
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, ratingTextView, platformsTextView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_game_name);
            ratingTextView = itemView.findViewById(R.id.text_game_rating);
            platformsTextView = itemView.findViewById(R.id.text_game_platforms);
        }
    }
}

