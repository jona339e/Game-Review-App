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
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<GameCategory> gameCategories = new ArrayList<>();
        gameCategories.add(new GameCategory("Currently Playing"));
        gameCategories.add(new GameCategory("Plan to Play"));
        gameCategories.add(new GameCategory("Upcoming Games"));

        GameCategoryAdapter adapter = new GameCategoryAdapter(gameCategories);
        recyclerView.setAdapter(adapter);

        return view;
    }


}