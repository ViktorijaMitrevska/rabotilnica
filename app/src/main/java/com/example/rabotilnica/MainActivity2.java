package com.example.rabotilnica;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private ArrayList<String> email,start, dest, time, date ;
    private  ArrayList<Float> price;
    private ArrayList<Integer> img;
    myAdapter mAdapter;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        dbHandler = new DBHandler(this);

        String passengerEmail = getIntent().getStringExtra("userEmail");

        email = new ArrayList<>();
        start = new ArrayList<>();
        dest = new ArrayList<>();
        price = new ArrayList<>();
        time = new ArrayList<>();
        date = new ArrayList<>();
        img = new ArrayList<>();


        //сетирање на RecyclerView контејнерот
        mRecyclerView = findViewById(R.id.list);

        // сетирање на кориснички дефиниран адаптер myAdapter (посебна класа)
//        mAdapter = new myAdapter(this, email,car,reg,img);
        mAdapter = new myAdapter(this,email,start,dest,price,time,date,img,passengerEmail,dbHandler);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //прикачување на адаптерот на RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // и default animator (без анимации)
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        
        displayData();


    }

    private void displayData() {
        Cursor cursor = dbHandler.getDataForRides();
        if(cursor.getCount()==0){
            Toast.makeText(MainActivity2.this,"There are no rides to display.",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                email.add(cursor.getString(0));
                start.add(cursor.getString(1));
                dest.add(cursor.getString(2));
                price.add(cursor.getFloat(3));
                time.add(cursor.getString(4));
                date.add(cursor.getString(5));
                img.add(R.drawable.ic_action_name);

            }
        }
    }


}

