package com.example.gamereview;

import java.util.List;

public class Game {
    private String name;
    private List<Platform> platforms;
    private double rating;

    // Constructor
    public Game(String name, List<Platform> platforms, double rating) {
        this.name = name;
        this.platforms = platforms;
        this.rating = rating;
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public double getRating() {
        return rating;
    }

    // Platform inner class to handle platform objects
    public static class Platform {
        private String name;

        // Constructor
        public Platform(String name) {
            this.name = name;
        }

        // Getter
        public String getName() {
            return name;
        }
    }
}
