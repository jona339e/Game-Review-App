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

        // Convert HashSet to List for the adapter
        List<Game> currentlyPlayingGames = User.getInstance().getSelectedGamesAsList();

        // Set up the adapter
        GamesAdapter adapter = new GamesAdapter(currentlyPlayingGames, null); // No click listener for this fragment
        recyclerView.setAdapter(adapter);

        return view;
    }
}

