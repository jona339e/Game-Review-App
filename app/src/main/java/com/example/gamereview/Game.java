package com.example.gamereview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Game {
    private int id;
    private String name;
    private List<Platform> platforms; // Updated to store Platform objects
    private double rating;
    private Cover cover;



    // Constructor with name, platforms, rating, and imageUrl
    public Game(int id, String name, List<Platform> platforms, double rating, Cover cover) {
        this.id = id;
        this.name = name;
        this.platforms = platforms;
        this.rating = rating;
        this.cover = cover; // Set the imageUrl
    }


    // No-argument constructor
    public Game() {
    }


    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public double getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        if (cover != null && cover.getUrl() != null) {
            return "https:" + cover.getUrl();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", platforms=" + platforms +
                ", rating=" + rating +
                ", imageUrl='" + getImageUrl() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Game game = (Game) obj;
        return id == game.id; // Assuming 'id' is a unique identifier for each game
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Ensure this matches the property used in equals()
    }

    public Map<String, Object> toMap() {
        Map<String, Object> gameMap = new HashMap<>();
        gameMap.put("id", id);
        gameMap.put("name", name);
        gameMap.put("rating", rating);

        // Convert platforms list to a list of maps
        List<Map<String, Object>> platformMaps = new ArrayList<>();
        for (Platform platform : platforms) {
            Map<String, Object> platformMap = new HashMap<>();
            platformMap.put("id", platform.getId());
            platformMap.put("name", platform.getName());
            platformMaps.add(platformMap);
        }
        gameMap.put("platforms", platformMaps);

        // Convert cover to map if it's not null
        if (cover != null) {
            Map<String, Object> coverMap = new HashMap<>();
            coverMap.put("url", cover.getUrl());
            gameMap.put("cover", coverMap);
        }

        return gameMap;
    }

}

