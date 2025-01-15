package com.example.gamereview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CurrentlyPlayingGames extends Fragment {

    private RecyclerView recyclerView;

    public CurrentlyPlayingGames() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currently_playing, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_currently_playing);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Example list of currently playing games
        List<Game> currentlyPlayingGames = new ArrayList<>();
        currentlyPlayingGames.add(new Game("The Witcher 3", Arrays.asList("PC", "PlayStation 5"), 9.8));
        currentlyPlayingGames.add(new Game("Hollow Knight", Arrays.asList("Nintendo Switch", "PC"), 9.5));
        currentlyPlayingGames.add(new Game("Cyberpunk 2077", Arrays.asList("Xbox Series X", "PC"), 8.7));

        // Set up the adapter
        GamesAdapter adapter = new GamesAdapter(currentlyPlayingGames);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
