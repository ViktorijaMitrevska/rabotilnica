package com.example.rabotilnica;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rabotilnica.databinding.ActivityRegisterBinding;
import com.example.rabotilnica.databinding.FragmentMyBinding;

public class MyFragment extends Fragment {

    private FragmentMyBinding binding;
    private DBHandler dbHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(inflater, container, false);
        dbHandler = new DBHandler(requireContext());

        // Set up the UI behavior
        setupListeners();

        return binding.getRoot();
    }

    private void setupListeners() {
        binding.rgroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.driver) {
                binding.driverDetails.setVisibility(View.VISIBLE);
            } else {
                binding.driverDetails.setVisibility(View.GONE);
            }
        });

        binding.RegisterButton.setOnClickListener(view -> {
            String name = binding.RegisterName.getText().toString();
            String email = binding.RegisterEmail.getText().toString();
            String password = binding.RegisterPassword.getText().toString();
            String confirmPass = binding.RegisterConfirmPassword.getText().toString();

            int selectedTypeId = binding.rgroup.getCheckedRadioButtonId();
            RadioButton selectedUserType = binding.getRoot().findViewById(selectedTypeId);
            String userType = (selectedUserType != null) ? selectedUserType.getText().toString() : null;

            String carType = null;
            String carRegistration = null;
            Float rating = null;
            if ("Driver".equals(userType)) {
                carType = binding.CarType.getText().toString();
                carRegistration = binding.CarRegistration.getText().toString();
            }

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || ("Driver".equals(userType) && (carType.isEmpty() || carRegistration.isEmpty()))) {
                Toast.makeText(requireContext(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
            } else {
                if (password.equals(confirmPass)) {
                    Boolean checkUserEmail = dbHandler.checkEmail(email);

                    if (!checkUserEmail) {
                        Boolean insert = dbHandler.insertData(name, email, password, userType, carType, carRegistration, rating);

                        if (insert) {
                            Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(requireContext(), LoginActivity.class);

                            startActivity(intent);
                        } else {
                            Toast.makeText(requireContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "User already exists!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Password incorrect.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.LoginRedirect.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}
