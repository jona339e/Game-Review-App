package com.example.gamereview;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class HomePage extends AppCompatActivity {

    private Map<Integer, Runnable> navigationActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Navigation menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // load the initial fragment so the view isn't empty
        loadHomeFragment();

        // populate list of navigations with a corresponding function
        initializeNavigationActions();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Check if the itemId exists in the map
            if (navigationActions.containsKey(item.getItemId())) {
                // Execute the stored function
                navigationActions.get(item.getItemId()).run();
                return true;
            }
            return false;
        });

        GameManager.getInstance().fetchGamesForUser(this);

    }

    private void initializeNavigationActions() {
        // Create a HashMap to map item IDs to actions
        navigationActions = new HashMap<>();

        // Add item IDs and corresponding actions
        navigationActions.put(R.id.navigation_home, this::loadHomeFragment);
        navigationActions.put(R.id.navigation_games, this::loadGamesFragment);
        navigationActions.put(R.id.navigation_account, this::loadAccountFragment);
    }


    // Methods to load different layouts
    private void loadHomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_container, new HomeFragment())
                .commit();
    }


    private void loadGamesFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_container, new GamesFragment())
                .commit();
    }

    private void loadAccountFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_container, new AccountFragment())
                .commit();
    }



}