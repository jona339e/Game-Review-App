package com.example.gamereview;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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


//    public void addGameToUser(Game game, Context context) {
//        Map<String, Object> test = new HashMap<>();
//        test.put("test1", "1");
//        test.put("test2", "2");
//        test.put("test3", "3");
//
//        db.collection("test")
//                .add(test)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("Firebase Insert", "DocumentSnapshot added with ID: " + documentReference.getId());
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Firebase Insert", "Error Adding Document", e);
//                    }
//                });
//
//
//    }


    public void addGameToUser(Game game, Context context) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Reference to the user's document in Firestore
            DocumentReference userDocRef = db.collection("users").document(userId);

            // Add the game to the user's selectedGames field
            User user = User.getInstance();
            user.getSelectedGames().add(game); // Add the new game to the selectedGames

            // Save the updated User object to Firestore
            userDocRef.set(user.toMap(), SetOptions.merge()) // Merge to avoid overwriting other data
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Firebase Insert", "User document successfully updated!");
                        Toast.makeText(context, "Game added successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firebase Insert", "Error adding game to user", e);
                        Toast.makeText(context, "Failed to add game", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Log.e("GameManager", "No authenticated user found");
        }
    }


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
                                List<Map<String, Object>> selectedGamesData = (List<Map<String, Object>>) documentSnapshot.get("selectedGames");

                                if (selectedGamesData != null) {
                                    // Create a HashSet to hold the deserialized Game objects
                                    HashSet<Game> selectedGames = new HashSet<>();

                                    // Deserialize each game from the map
                                    for (Map<String, Object> gameData : selectedGamesData) {
                                        Game game = deserializeGame(gameData);
                                        if (game != null) {
                                            selectedGames.add(game);
                                        }
                                    }

                                    // Update the selectedGames in the User singleton
                                    User.getInstance().setSelectedGames(selectedGames);

                                    // Optionally, update UI or RecyclerView with the games
                                    // Example: recyclerViewAdapter.setGames(new ArrayList<>(selectedGames));
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

    // Helper method to deserialize a game from the data map
    private Game deserializeGame(Map<String, Object> gameData) {
        try {
            int id = ((Long) gameData.get("id")).intValue();
            String name = (String) gameData.get("name");
            double rating = (Double) gameData.get("rating");

            // Assuming Platform and Cover are also stored in the game data
            List<Map<String, Object>> platformsData = (List<Map<String, Object>>) gameData.get("platforms");
            List<Platform> platforms = new ArrayList<>();
            if (platformsData != null) {
                for (Map<String, Object> platformData : platformsData) {
                    String platformName = (String) platformData.get("name");
                    platforms.add(new Platform(platformName));
                }
            }

            Map<String, Object> coverData = (Map<String, Object>) gameData.get("cover");
            Cover cover = null;
            if (coverData != null) {
                String url = (String) coverData.get("url");
                cover = new Cover();
                cover.setUrl(url);
            }

            return new Game(id, name, platforms, rating, cover);
        } catch (Exception e) {
            Log.e("GameManager", "Error deserializing game", e);
            return null; // In case of error, return null
        }
    }

}
