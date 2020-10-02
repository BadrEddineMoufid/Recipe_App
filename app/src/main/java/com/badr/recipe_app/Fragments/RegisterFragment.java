package com.badr.recipe_app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.badr.recipe_app.ApiClient;
import com.badr.recipe_app.ApiInterface;
import com.badr.recipe_app.Model.authResponse;
import com.badr.recipe_app.Model.registerRequest;
import com.badr.recipe_app.R;
import com.badr.recipe_app.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
    private ApiInterface apiInterface;
    private SharedPreferences sharedPreferences;
    private static final String TAG = "RegisterFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_register, container, false);

        //setting the views
        EditText name = rootView.findViewById(R.id.register_name_input);
        EditText email = rootView.findViewById(R.id.register_email_input);
        EditText password = rootView.findViewById(R.id.register_password_input);
        Button registerButton = rootView.findViewById(R.id.register_button);
        Button goToLoginButton = rootView.findViewById(R.id.go_to_log_in_button);

        //click listener for login button
        goToLoginButton.setOnClickListener(v->{
            NavHostFragment.findNavController(this).navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment());
        });
        //click listener for register button
        registerButton.setOnClickListener(v -> {
           register(name.getText().toString(), email.getText().toString(), password.getText().toString());
        });

        return rootView;
    }

    private void register(String name, String email, String password) {
        registerRequest registerRequest = new registerRequest(name, email, password);

        Call<authResponse> registerCall = apiInterface.register(Utils.REGISTER_URL_LOCALHOST, registerRequest);

        registerCall.enqueue(new Callback<authResponse>() {
            @Override
            public void onResponse(Call<authResponse> call, Response<authResponse> response) {
                switch (response.code())
                {
                    case 200:
                        //save tokens
                        setTokens(response);
                        //redirect to user fragment
                        NavHostFragment.findNavController(RegisterFragment.this).navigate(RegisterFragmentDirections.actionRegisterFragmentToUserFragment());
                        break;
                    case 400:
                        Toast.makeText(getContext(), "registration failed", Toast.LENGTH_SHORT).show();
                        break;
                    case 500:
                        Toast.makeText(getContext(),"server error ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<authResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Toast.makeText(getContext(), "register request failed ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTokens(Response<authResponse> response){
        //saving tokens to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ACCESS_TOKEN", response.body().getTokens().getAccessToken());
        editor.putString("REFRESH_TOKEN", response.body().getTokens().getRefreshToken());
        editor.putString("USER_NAME", response.body().getUser().getName());
        editor.putString("USER_EMAIL", response.body().getUser().getEmail());
        editor.apply();
        Toast.makeText(getContext(),"Tokens Set, login successful ", Toast.LENGTH_SHORT).show();
    }
}