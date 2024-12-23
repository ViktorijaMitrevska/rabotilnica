package com.example.rabotilnica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.rabotilnica.databinding.ActivityLoginBinding;
import com.example.rabotilnica.databinding.ActivityRegisterBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);

        dbHandler = new DBHandler(this);

        binding.LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.LoginEmail.getText().toString();
                String password = binding.LoginPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty() )
                    Toast.makeText(LoginActivity.this,"Please fill all the inputs.", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = dbHandler.checkEmailPassword(email,password);

                    if(checkCredentials){
                        Toast.makeText(LoginActivity.this,"Success!", Toast.LENGTH_SHORT).show();
                        Log.d("LoginRedirect", "Button clicked");
                        String userType = dbHandler.checkUserType(email, password);
                        
                        Intent intent = "Driver".equals(userType) ?
                                new Intent(LoginActivity.this, DriverActivity.class) :
                                new Intent(LoginActivity.this, MainActivity2.class);

                        intent.putExtra("userEmail",email);
                        startActivity(intent);
                    } else{
                        Toast.makeText(LoginActivity.this,"Password incorrect!", Toast.LENGTH_SHORT).show();
                    }
                }
//                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("DRIVER_EMAIL", email);
//                editor.apply();
            }
        });

        binding.RegisterRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginRedirect", "Button clicked");
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        if(findViewById(R.id.main) != null){
            FragmentManager manager = this.getSupportFragmentManager();

            manager.beginTransaction()
                    .commit();
        }


    }
}