package com.example.gamereview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameCategoryAdapter extends RecyclerView.Adapter<GameCategoryAdapter.ViewHolder> {

    private final List<GameCategory> gameCategories;
    private final FragmentManager fragmentManager;

    public GameCategoryAdapter(List<GameCategory> gameCategories, FragmentManager fragmentManager) {
        this.gameCategories = gameCategories;
        this.fragmentManager = fragmentManager;
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

        // Set up click listener for each card
        holder.itemView.setOnClickListener(v -> {
            Fragment fragment = null;

            // Navigate to different fragments based on the card clicked
            switch (gameCategory.getTitle()) {
                case "Currently Playing":
                    fragment = new CurrentlyPlayingGames();
                    break;
                case "Plan to Play":
                    fragment = new PlanToPlayFragment();
                    break;
                case "Upcoming Games":
                    fragment = new UpcomingGamesFragment();
                    break;
            }

            if (fragment != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_container, fragment) // Replace with the correct container ID
                        .addToBackStack(null)
                        .commit();
            }
        });
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
