package com.example.gamereview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//      button callbacks

        Button signInButton = findViewById(R.id.signUpButton);

        signInButton.setOnClickListener( view ->{
            // TODO
            // display message if user is created before redirecting to login
            // make errors if inputs are invalid
            startActivity(
                    new Intent(this, LoginActivity.class)
            );
        });

        TextView signInRedirect = findViewById(R.id.signInRedirect);

        signInRedirect.setOnClickListener( view ->{
            startActivity(
                    new Intent(this, LoginActivity.class)
            );
        });
    }
}