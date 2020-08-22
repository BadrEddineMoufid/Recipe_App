package com.badr.recipe_app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.badr.recipe_app.R;

public class RegisterFragment extends Fragment {
    private EditText name, email, password;
    private Button registerButton, goTologinButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_register, container, false);

        name = rootView.findViewById(R.id.register_name_input);
        email = rootView.findViewById(R.id.register_email_input);
        password = rootView.findViewById(R.id.register_password_input);

        registerButton = rootView.findViewById(R.id.register_button);
        goTologinButton = rootView.findViewById(R.id.go_to_log_in_button);

        goTologinButton.setOnClickListener(v->{
            NavHostFragment.findNavController(this).navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment());
        });

        registerButton.setOnClickListener(v -> {

        });

        return rootView;
    }
}