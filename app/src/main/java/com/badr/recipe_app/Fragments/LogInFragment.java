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
import com.badr.recipe_app.Model.logInRequest;
import com.badr.recipe_app.Model.authResponse;
import com.badr.recipe_app.R;
import com.badr.recipe_app.Utils;
import com.badr.recipe_app.sessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogInFragment extends Fragment {

    private ApiInterface apiInterface;
    private static final String TAG = "LogInFragment";
    private EditText email, password;
    private sessionManager sessionManager ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new sessionManager(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflating layout
        View rootView =  inflater.inflate(R.layout.fragment_log_in, container, false);
        //setting the views

        Button goToRegisterButton, logInButton;
        email = rootView.findViewById(R.id.log_in_email_input);
        password = rootView.findViewById(R.id.log_in_password_input);
        goToRegisterButton = rootView.findViewById(R.id.go_to_register_button);
        logInButton = rootView.findViewById(R.id.log_in_button);

        goToRegisterButton.setOnClickListener(V->{
            NavHostFragment.findNavController(this).navigate(LogInFragmentDirections.actionLogInFragmentToRegisterFragment());
        });

        logInButton.setOnClickListener(v->{
            logIn(email.getText().toString(), password.getText().toString());
        });

        return rootView;
    }

    private void logIn(String email, String passwordString) {
        logInRequest logInRequest = new logInRequest(email, passwordString);

        //making post request to backend with login info
        Call<authResponse> logInResponseCall = apiInterface.logIn(Utils.LOGIN_URL_LOCALHOST, logInRequest);
        logInResponseCall.enqueue(new Callback<authResponse>() {
            @Override
            public void onResponse(Call<authResponse> call, Response<authResponse> response) {
                switch (response.code()){
                    case 200:
                        //Log.d(TAG, "loginResponse: "  + response.body());
                        //saving the tokens and user info in sharedPreferences
                        sessionManager.logIn(response.body().getUser().getName(), response.body().getUser().getEmail(), response.body().getTokens().getAccessToken(), response.body().getTokens().getRefreshToken());

                        //navigating to userFragment after login
                        NavHostFragment.findNavController(LogInFragment.this).navigate(LogInFragmentDirections.actionLogInFragmentToUserFragment());
                        break;
                    case 400:
                        try {
                            JSONObject jsonError = new JSONObject(response.errorBody().string());
                            password.setError(jsonError.get("error").toString());

                            Toast.makeText(getContext(),"login failed "+ jsonError.get("error").toString(), Toast.LENGTH_SHORT).show();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    case 500:
                        Toast.makeText(getContext(),"server error ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<authResponse> call, Throwable t) {
                Log.e(TAG, "Error occurred: " + t.getMessage());
                Toast.makeText(getContext(), "LogIn request failed ", Toast.LENGTH_SHORT).show();
            }
        });
    }




}