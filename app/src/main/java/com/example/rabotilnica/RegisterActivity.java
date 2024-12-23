package com.example.rabotilnica;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.rabotilnica.databinding.ActivityRegisterBinding;

public class  RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        binding = ActivityRegisterBinding.inflate((getLayoutInflater()));
//        setContentView(binding.getRoot());

        Fragment fragment;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragment = new FragmentLand();
        } else {
            fragment = new MyFragment();
        }

        EdgeToEdge.enable(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

//        dbHandler = new DBHandler(this);
//
//        binding.rgroup.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId == R.id.driver) {
//                binding.driverDetails.setVisibility(View.VISIBLE);
//            } else {
//                binding.driverDetails.setVisibility(View.GONE);
//            }
//        });
//
//        binding.RegisterButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                String name = binding.RegisterName.getText().toString();
//                String email = binding.RegisterEmail.getText().toString();
//                String password = binding.RegisterPassword.getText().toString();
//                String confirmPass = binding.RegisterConfirmPassword.getText().toString();
//
//                int selectedTypeId = binding.rgroup.getCheckedRadioButtonId();
//                RadioButton selectedUserType = findViewById(selectedTypeId);
//                String userType = (selectedUserType != null) ? selectedUserType.getText().toString() : null;
//
//                String carType = null;
//                String carRegistration = null;
//                Float rating = null;
//                if("Driver".equals(userType)){
//                     carType =  binding.CarType.getText().toString();
//                     carRegistration = binding.CarRegistration.getText().toString();
//                }
//
//
//                if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty() ||("Driver".equals(userType) && (carType.isEmpty() || carRegistration.isEmpty())) )
//                    Toast.makeText(RegisterActivity.this,"Please fill all the fields.", Toast.LENGTH_SHORT).show();
//                else{
//                    if(password.equals(confirmPass)){
//                        Boolean checkUserEmail = dbHandler.checkEmail(email);
//
//                        if(!checkUserEmail){
//                            Boolean insert = dbHandler.insertData(name,email, password, userType,carType,carRegistration,rating);
//
//                            if(insert){
//                                Toast.makeText(RegisterActivity.this,"Success!",Toast.LENGTH_SHORT).show();
//                                Intent intent = "Driver".equals(userType) ?
//                                        new Intent(RegisterActivity.this, DriverActivity.class) :
//                                        new Intent(RegisterActivity.this, MainActivity2.class);
//
//                                startActivity(intent);
//                            } else {
//                                Toast.makeText(RegisterActivity.this,"  Failed!",Toast.LENGTH_SHORT).show();
//                            }
//                        } else{
//                            Toast.makeText(RegisterActivity.this,"User already exists!",Toast.LENGTH_SHORT).show();
//                        }
//                    }  else{
//                        Toast.makeText(RegisterActivity.this,"Password incorrect.",Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        });
//
//        binding.LoginRedirect.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
   }
}