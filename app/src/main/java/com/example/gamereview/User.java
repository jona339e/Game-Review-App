package com.example.gamereview;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class User {
    private static User instance;
    private HashSet<Game> selectedGames;

    // Private constructor to prevent instantiation
    private User() {
        selectedGames = new HashSet<>();
    }

    // Public method to get the instance of User
    public static User getInstance() {
        if (instance == null) {
            instance = new User(); // Lazy initialization
        }
        return instance;
    }

    // Getter and setter for selectedGames
    public HashSet<Game> getSelectedGames() {
        return selectedGames;
    }

    public void setSelectedGames(HashSet<Game> selectedGames) {
        this.selectedGames = selectedGames;
    }

    // Method to return selectedGames as a List for easier RecyclerView implementation
    public List<Game> getSelectedGamesAsList() {
        return new ArrayList<>(selectedGames);
    }

    // Optionally, clear instance (useful for logout)
    public static void clearInstance() {
        instance = null;
    }
}
