package com.example.rabotilnica;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rabotilnica.databinding.ActivityChoosenRideBinding;
import com.example.rabotilnica.databinding.ActivityDriverBinding;

import java.util.Locale;

public class ChoosenRideActivity extends AppCompatActivity {

    DBHandler dbHandler;
    ActivityChoosenRideBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choosen_ride);

        binding = ActivityChoosenRideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String passengerEmail = getIntent().getStringExtra("passengerEmail");
        String driverEmail = getIntent().getStringExtra("rideDriverEmail");
        int rideId = getIntent().getIntExtra("rideId",-1);

        if (driverEmail == null) {
            Log.e("ChoosenRideActivity", "driverEmail is null");
            Toast.makeText(this, "Error: Driver email not received", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHandler = new DBHandler(this);
        Cursor cursor = dbHandler.getData(driverEmail); // Use the driver's email as input

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(0);
            String email = cursor.getString(1);
            float rating = cursor.getFloat(2);
            String carType = cursor.getString(3);
            String regPlate = cursor.getString(4);

            // Now you can set this data into your UI
            TextView driverName = findViewById(R.id.driverName);
            TextView driverEmail1 = findViewById(R.id.driverEmail1);
            TextView driverRating = findViewById(R.id.driverRating);
            TextView driverCarType = findViewById(R.id.driverCar);
            TextView driverRegPlate = findViewById(R.id.driverRegistration);

            driverName.setText(name);
            driverEmail1.setText(email);
            driverRating.setText(String.format(Locale.getDefault(), "%.1f", rating));
            driverCarType.setText(carType);
            driverRegPlate.setText(regPlate);

            cursor.close();
        } else {
            Toast.makeText(this, "Driver not found", Toast.LENGTH_SHORT).show();
        }


        binding.rateDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoosenRideActivity.this,RatingActivity.class);
                intent.putExtra("rateUserEmail", driverEmail);
                startActivity(intent);
            }
        });

        binding.chooseDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rideId != -1) {
                    dbHandler.updatePassenger(rideId, passengerEmail);
                    Toast.makeText(ChoosenRideActivity.this, "Ride chosen successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChoosenRideActivity.this, "Error: Ride ID is missing.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}