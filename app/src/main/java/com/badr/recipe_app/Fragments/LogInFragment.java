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


public class LogInFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_log_in, container, false);

        EditText email, password;
        Button goToRegisterButton, logInButton;
        email = rootView.findViewById(R.id.log_in_email_input);
        password = rootView.findViewById(R.id.log_in_password_input);
        goToRegisterButton = rootView.findViewById(R.id.go_to_register_button);
        logInButton = rootView.findViewById(R.id.log_in_button);

        goToRegisterButton.setOnClickListener(V->{
            NavHostFragment.findNavController(this).navigate(LogInFragmentDirections.actionLogInFragmentToRegisterFragment());
        });

        return rootView;
    }
}