package com.example.rabotilnica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rabotilnica.databinding.ActivityAddRideBinding;
import com.example.rabotilnica.databinding.ActivityRegisterBinding;

public class AddRideActivity extends AppCompatActivity {

    DBHandler dbHandler;

    ActivityAddRideBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ride);

        dbHandler = new DBHandler(this);

        binding = ActivityAddRideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String driverEmail = getIntent().getStringExtra("driverEmail");

        binding.submitRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = binding.location.getText().toString();
                String[] parts = location.split(",");
                Float lng = null,ltd = null;
                if(parts.length == 2){
                     ltd = Float.parseFloat(parts[0].trim());
                     lng = Float.parseFloat(parts[1].trim());
                }
                else {
                    Toast.makeText(AddRideActivity.this, "Please enter valid coordinates.", Toast.LENGTH_SHORT).show();
                }

                String destination = binding.destination.getText().toString();
                String[] parts1 = destination.split(",");
                Float lng1 = null,ltd1 = null;
                if(parts1.length == 2){
                     ltd1 = Float.parseFloat(parts1[0].trim());
                     lng1 = Float.parseFloat(parts1[1].trim());
                }
                else {
                    Toast.makeText(AddRideActivity.this, "Please enter valid coordinates.", Toast.LENGTH_SHORT).show();
                }

                String price1 = binding.price.getText().toString();
                Float price = Float.parseFloat(price1);

                String time = binding.time.getText().toString();
                String date = binding.date.getText().toString();

                String start = binding.startCity.getText().toString();
                String dest = binding.destinationCity.getText().toString();

                if(location.isEmpty() || destination.isEmpty() || price1.isEmpty() || time.isEmpty() || date.isEmpty() || start.isEmpty() || dest.isEmpty()){
                    Toast.makeText(AddRideActivity.this,"Please fill all the values.",Toast.LENGTH_SHORT).show();
                } else {
                    Boolean insert = dbHandler.insertRide(driverEmail,null,price,time,date,ltd,lng,ltd1,lng1,start,dest);

                    if(insert){
                        Toast.makeText(AddRideActivity.this,"Successfully added ride!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddRideActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddRideActivity.this,"Failed to add ride!", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });



    }
}