package com.example.rabotilnica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rabotilnica.databinding.ActivityDriverBinding;

import java.util.ArrayList;

public class DriverActivity extends AppCompatActivity {
    ActivityDriverBinding binding;
    RecyclerView mRecyclerView;
    private ArrayList<String> email,name;

    private ArrayList<Integer> img;

    private ArrayList<Float> rating;
    myAdapter1 mAdapter;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_driver);
        dbHandler = new DBHandler(this);
        binding = ActivityDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String driverEmail = getIntent().getStringExtra("userEmail");

        showDriverRating(driverEmail);


        binding.addRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(DriverActivity.this, AddRideActivity.class);
                intent.putExtra("driverEmail", driverEmail); // Email of the driver
                startActivity(intent);
            }
        });

        email = new ArrayList<>();
        name = new ArrayList<>();
        img = new ArrayList<>();
        rating = new ArrayList<>();

        //сетирање на RecyclerView контејнерот
        mRecyclerView = findViewById(R.id.list1);

        // сетирање на кориснички дефиниран адаптер myAdapter (посебна класа)
        mAdapter = new myAdapter1(this, img,name,email,rating);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //прикачување на адаптерот на RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // и default animator (без анимации)
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        displayData();

//        Cursor passengerCursor = dbHandler.getPassengers(driverEmail);
//        if (passengerCursor != null) {
//            try {
//                if (passengerCursor.getCount() == 0) {
//                    Toast.makeText(DriverActivity.this, "There are no passengers to display.", Toast.LENGTH_SHORT).show();
//                } else {
//                    while (passengerCursor.moveToNext()) {
//                        String passengerEmail = passengerCursor.getString(0);
//                        // Fetch and display data for each passenger email
//                        displayData(passengerEmail);
//                    }
//                }
//            } finally {
//                passengerCursor.close();
//            }
//        } else {
//            Toast.makeText(this, "Error retrieving passengers.", Toast.LENGTH_SHORT).show();
//        }

    }

    private void showDriverRating(String driverEmail) {
        Cursor cursor = dbHandler.getRating(driverEmail);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                float rating = cursor.getFloat(0);
                TextView ratingTextView = findViewById(R.id.rating);
                ratingTextView.setText("Your rating is: " + rating);
            } else {
                Toast.makeText(this, "No rating found for this driver", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Error retrieving rating", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayData() {

        Cursor cursor = dbHandler.getData1();
        if(cursor.getCount()==0){
            Toast.makeText(DriverActivity.this,"There are no passengers to display.",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                email.add(cursor.getString(0));
                name.add(cursor.getString(1));
                Float currentRating = cursor.isNull(2) ? 0.0f : cursor.getFloat(2);
                rating.add(currentRating);
                img.add(R.drawable.ic_action_name);

            }
        }
    }
//private void displayData(String passengerEmail) {
//    Cursor detailsCursor = dbHandler.getData1(passengerEmail);
//    if (detailsCursor != null) {
//        try {
//            if (detailsCursor.moveToFirst()) {
//                email.add(detailsCursor.getString(0)); // Email
//                name.add(detailsCursor.getString(1));  // Name
//                Float currentRating = detailsCursor.isNull(2) ? 0.0f : detailsCursor.getFloat(2);
//                rating.add(currentRating);
//                img.add(R.drawable.ic_action_name);    // Placeholder Image
//            }
//        } finally {
//            detailsCursor.close();
//        }
//    } else {
//        Toast.makeText(this, "Error retrieving passenger details.", Toast.LENGTH_SHORT).show();
//    }
////
////    // Notify the adapter to refresh the RecyclerView
////    mAdapter.notifyDataSetChanged();
//}
}