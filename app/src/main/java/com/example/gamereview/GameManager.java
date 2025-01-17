package com.example.gamereview;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashSet;

public class GameManager {
    private static GameManager instance;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    // Private constructor to prevent instantiation
    private GameManager() {
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    // Singleton instance
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void addGameToUser(Game game, Context context) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Log if the user is authenticated
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Log.d("GameManager", "User ID: " + userId);

            // Reference to the user's document in Firestore
            DocumentReference userDocRef = db.collection("users").document(userId);

            // Log the game data that is being added
            Log.d("GameManager", "Adding game: " + game.getName() + ", Game ID: " + game.getId());

            // Use set() to create the document if it doesn't exist, and arrayUnion to add the game
            userDocRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        // If the document exists, update the selectedGames field
                        userDocRef.update("selectedGames", FieldValue.arrayUnion(game))
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("GameManager", "Game added successfully to Firestore");
                                    Toast.makeText(context, "Game added successfully", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("GameManager", "Failed to add game", e);
                                    Toast.makeText(context, "Failed to add game", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // If the document doesn't exist, create it with the new game in selectedGames
                        userDocRef.set(User.getInstance(), SetOptions.merge())
                                .addOnSuccessListener(aVoid -> {
                                    userDocRef.update("selectedGames", FieldValue.arrayUnion(game))
                                            .addOnSuccessListener(aVoid1 -> {
                                                Log.d("GameManager", "Game added successfully after creating document");
                                                Toast.makeText(context, "Game added successfully", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e2 -> {
                                                Log.e("GameManager", "Failed to add game after creating document", e2);
                                                Toast.makeText(context, "Failed to add game", Toast.LENGTH_SHORT).show();
                                            });
                                })
                                .addOnFailureListener(e1 -> {
                                    Log.e("GameManager", "Failed to create user document", e1);
                                    Toast.makeText(context, "Failed to create user document", Toast.LENGTH_SHORT).show();
                                });
                    }
                } else {
                    Log.e("GameManager", "Failed to get user document", task.getException());
                    Toast.makeText(context, "Failed to get user document", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Log if the user is not authenticated
            Log.e("GameManager", "No authenticated user found");
        }
    }




    // Method to fetch the list of games for the logged-in user
    public void fetchGamesForUser(Context context) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Reference to the user's document in Firestore
            DocumentReference userDocRef = db.collection("users").document(userId);

            // Get the user's selected games
            userDocRef.get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                // Retrieve the selectedGames field
                                HashSet<Game> selectedGames = (HashSet<Game>) documentSnapshot.get("selectedGames");

                                if (selectedGames != null) {
                                    // Update the selectedGames in the User singleton
                                    User.getInstance().setSelectedGames(selectedGames);

                                    // Optionally, update UI or RecyclerView with the games
                                    // Example: recyclerViewAdapter.setGames(selectedGames);
                                }
                            } else {
                                Toast.makeText(context, "No games found for this user", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Failed to fetch games", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
