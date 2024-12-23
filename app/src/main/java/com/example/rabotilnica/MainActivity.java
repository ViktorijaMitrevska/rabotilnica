package com.example.rabotilnica;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync( this);
        }

        dbHandler = new DBHandler(this);
    }

    DBHandler dbHandler;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(41.6086, 21.7453);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 8));

//        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//        String driverEmail = sharedPreferences.getString("DRIVER_EMAIL", null);

        Cursor cursor = dbHandler.getLatLng();

        Float originLat = null, originLng = null, destinationLat = null, destinationLng = null;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                originLat = cursor.getFloat(0);  // First column: originLat
                originLng = cursor.getFloat(1);  // Second column: originLng
                destinationLat = cursor.getFloat(2);  // Third column: destinationLat
                destinationLng = cursor.getFloat(3);  // Fourth column: destinationLng

            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No rides found for this driver.", Toast.LENGTH_SHORT).show();
        }

        googleMap.addMarker(new MarkerOptions().position(new LatLng(originLat,originLng)).title("Start"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(destinationLat,destinationLng)).title("End"));
        googleMap.addPolyline(new PolylineOptions().add(new LatLng(originLat,originLng)).add(new LatLng(destinationLat,destinationLng)));

    }
}

