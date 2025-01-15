package com.example.gamereview;

import java.util.List;

public class Game {
    private String name;
    private List<String> platforms; // Changed to a List to store multiple platforms
    private double rating;

    // Constructor
    public Game(String name, List<String> platforms, double rating) {
        this.name = name;
        this.platforms = platforms;
        this.rating = rating;
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

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
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
                '}';
    }
}
