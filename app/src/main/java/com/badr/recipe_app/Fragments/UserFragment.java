package com.badr.recipe_app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.recipe_app.Model.User;
import com.badr.recipe_app.R;

public class UserFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(!sharedPreferences.contains("ACCESS_TOKEN")){
            NavHostFragment.findNavController(UserFragment.this).navigate(UserFragmentDirections.actionUserFragmentToRegisterFragment());
            return rootView;
        }

        TextView userName = rootView.findViewById(R.id.userFragment_userName);
        userName.setText(sharedPreferences.getString("USER_NAME", "USER_NAME"));
        Button logOutButton = rootView.findViewById(R.id.logout_button);
        logOutButton.setOnClickListener(v ->{
            logOut();
            Toast.makeText(getContext(),"Logged out ", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(UserFragment.this).navigate(UserFragmentDirections.actionUserFragmentToLogInFragment());
        });

        return rootView;
    }
    private void logOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("ACCESS_TOKEN");
        editor.remove("REFRESH_TOKEN");
        editor.remove("USER_NAME");
        editor.remove("USER_EMAIL");
        editor.apply();

    }

}