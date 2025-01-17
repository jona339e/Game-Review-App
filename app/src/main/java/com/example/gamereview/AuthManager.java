package com.example.gamereview;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthManager {
    private FirebaseAuth firebaseAuth;

    public AuthManager() {
        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
    }

    // Method to sign up a new user
    public void signUp(String email, String password, OnAuthCompleteListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign-up success
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        listener.onSuccess(user);
                    } else {
                        // Sign-up failed
                        listener.onFailure(task.getException());
                    }
                });
    }

    // Method to sign in an existing user
    public void signIn(String email, String password, OnAuthCompleteListener listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        listener.onSuccess(user);
                    } else {
                        listener.onFailure(task.getException());
                    }
                });
    }

    // Callback interface for success and failure
    public interface OnAuthCompleteListener {
        void onSuccess(FirebaseUser user);
        void onFailure(Exception e);
    }
}
