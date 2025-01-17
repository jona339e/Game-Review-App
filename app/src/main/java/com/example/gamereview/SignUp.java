package com.example.gamereview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "SignUpActivity"; // For logging purposes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Setup UI elements
        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(view -> {
            String email = ((EditText) findViewById(R.id.emailInput)).getText().toString().trim();
            String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString().trim();
            String confirmPassword = ((EditText) findViewById(R.id.confirmPasswordInput)).getText().toString().trim();

            Log.d(TAG, "Sign-up attempt with email: " + email);  // Log email for debugging purposes

            if (password.equals(confirmPassword)) {
                if (isValidEmail(email) && isValidPassword(password)) {
                    createUser(email, password);
                } else {
                    Toast.makeText(SignUp.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private void createUser(String email, String password) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE); // Show progress bar

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE); // Hide progress bar

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "User created successfully: " + user.getEmail());  // Log success
                        startActivity(new Intent(SignUp.this, FrontPage.class)); // Go to HomeActivity
                        finish(); // Close the sign-up activity
                    } else {
                        // Log the error message
                        Log.e(TAG, "Sign-up failed", task.getException());
                        Toast.makeText(SignUp.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
