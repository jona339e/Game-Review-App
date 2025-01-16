package com.example.gamereview;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String name;
    private List<Platform> platforms; // Updated to store Platform objects
    private double rating;
    private String imageUrl; // Image URL added

    // Constructor with name, platforms, rating, and imageUrl
    public Game(String name, List<Platform> platforms, double rating, String imageUrl) {
        this.name = name;
        this.platforms = platforms;
        this.rating = rating;
        this.imageUrl = imageUrl; // Set the imageUrl
    }

    // Constructor for List<String> - uses helper method to convert to Platform objects, and takes an imageUrl
    public Game(String name, String[] platformNames, double rating, String imageUrl) {
        List<Platform> platformsList = new ArrayList<>();
        for (String platformName : platformNames) {
            platformsList.add(new Platform(platformName));
        }

        this.name = name;
        this.platforms = platformsList;
        this.rating = rating;
        this.imageUrl = imageUrl; // Set the imageUrl
    }

    // No-argument constructor
    public Game() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", platforms=" + platforms +
                ", rating=" + rating +
                ", imageUrl='" + imageUrl + '\'' + // Added imageUrl to toString
                '}';
    }
}

