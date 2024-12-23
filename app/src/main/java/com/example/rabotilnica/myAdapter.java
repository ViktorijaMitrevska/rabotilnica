package com.example.rabotilnica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rabotilnica.databinding.ActivityMyAdapterBinding;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

//    ActivityMyAdapterBinding binding;
    private Context mContext;
//    private ArrayList email, car, reg;
    private ArrayList email,start,dest,price,time,date;
    private ArrayList img;
    private String passengerEmail;

    private DBHandler dbHandler;

    // конструктор
    public myAdapter(Context mContext,ArrayList email, ArrayList start , ArrayList dest, ArrayList price,ArrayList time, ArrayList date, ArrayList img, String passengerEmail, DBHandler dbHandler) {
        this.mContext = mContext;
        this.email = email;
        this.start = start;
        this.dest = dest;
        this.price = price;
        this.time = time;
        this.date = date;
        this.img = img;
        this.passengerEmail = passengerEmail;
        this.dbHandler = dbHandler;
    }

    // Референца на views за секој податок
    // Комплексни податоци може да бараат повеќе views per item
    // Пристап до сите views за податок се дефинира во view holder

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView email, car, reg;
        public TextView email,start,dest,price, time, date;
        public ImageView Pic;
        public Button chooseButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email =  itemView.findViewById(R.id.driverEmail);
            start = itemView.findViewById(R.id.driverLocation);
            dest = itemView.findViewById(R.id.driverDestination);
            price = itemView.findViewById(R.id.ridePrice);
            time = itemView.findViewById(R.id.rideTime);
            date = itemView.findViewById(R.id.rideDate);
            Pic = itemView.findViewById(R.id.driverPic);
            chooseButton = itemView.findViewById(R.id.chooseRide);
        }

  }

    // Креирање нови views (повикано од layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_my_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    // Замена на содржината во view (повикано од layout manager)

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        String driverEmail = (String) email.get(i);
        String rideStart = (String) start.get(i);
        String rideDest =  (String) dest.get(i);
        Float ridePrice = (Float) price.get(i);
        String rideTime = (String) time.get(i);
        String rideDate = (String) date.get(i);

        Cursor cursor = dbHandler.getId(driverEmail,rideStart,rideDest,ridePrice,rideTime,rideDate);
        int rideId;
        if (cursor != null && cursor.moveToFirst()) {
            rideId = cursor.getInt(0);
            cursor.close();
        } else {
            rideId = -1;
        }

//        viewHolder.email.setText(String.valueOf(email.get(i)));
//        viewHolder.start.setText("From: " + start.get(i));
//        viewHolder.dest.setText("To: " + dest.get(i));
//        viewHolder.price.setText("Price: " + price.get(i));
//        viewHolder.time.setText(String.valueOf("Minutes: " + time.get(i)));
//        viewHolder.date.setText(String.valueOf(date.get(i)));
//        viewHolder.Pic.setImageResource(R.drawable.ic_action_name);

        viewHolder.email.setText(driverEmail);
        viewHolder.start.setText("From" + rideStart);
        viewHolder.dest.setText("To:" + rideDest);
        viewHolder.price.setText("Price" + ridePrice);
        viewHolder.time.setText("Minutes: " + rideTime);
        viewHolder.date.setText("Date:" + rideDate);
        viewHolder.Pic.setImageResource(R.drawable.ic_action_name);



        viewHolder.chooseButton.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ChoosenRideActivity.class);
            intent.putExtra("passengerEmail", passengerEmail); // Email of the passenger passed from the log in
            intent.putExtra("rideDriverEmail", email.get(i).toString()); // Pass the driver's email
            intent.putExtra("rideId", rideId);// Pass the id of the ride
            mContext.startActivity(intent);
        });

    }
    // Пресметка на големината на податочното множество (повикано од layout manager)
    @Override
    public int getItemCount() {
        return email.size();
    }

}