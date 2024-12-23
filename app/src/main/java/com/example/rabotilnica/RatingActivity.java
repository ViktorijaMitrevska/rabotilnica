package com.example.rabotilnica;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rabotilnica.databinding.ActivityChoosenRideBinding;
import com.example.rabotilnica.databinding.ActivityRatingBinding;

public class RatingActivity extends AppCompatActivity {

    ActivityRatingBinding binding;
    private RatingBar ratingBar;
    private Button submitButton;
    private DBHandler dbHandler;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rating);
        binding = ActivityRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String ratedUserEmail = getIntent().getStringExtra("rateUserEmail");

        ratingBar = findViewById(R.id.ratingBar);
        submitButton = findViewById(R.id.submitButton);

        dbHandler = new DBHandler(this);

        binding.submitButton.setOnClickListener(v -> {
            float rating = ratingBar.getRating();  // Get rating from RatingBar

            if (rating > 0) {  // Ensure a valid rating has been selected
                // Update the passenger's rating in the database
                boolean success = dbHandler.updateRating(ratedUserEmail, rating);

                if (success) {
                    Toast.makeText(this, "Rating updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to update rating.", Toast.LENGTH_SHORT).show();
                }

                finish(); // Return to the previous activity
            } else {
                Toast.makeText(this, "Please select a rating.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}