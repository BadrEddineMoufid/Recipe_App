package com.badr.recipe_app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Debug;
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
import com.badr.recipe_app.Model.logInResponse;
import com.badr.recipe_app.R;
import com.badr.recipe_app.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogInFragment extends Fragment {

    private ApiInterface apiInterface;
    private SharedPreferences sharedPreferences;
    private static final String TAG = "LogInFragment";
    private SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

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

        logInButton.setOnClickListener(v->{
            logIn(email.getText().toString(), password.getText().toString());
        });

        return rootView;
    }

    private void logIn(String email, String password) {
        logInRequest logInRequest = new logInRequest(email, password);

        //making post request to backend with login info
        Call<logInResponse> logInResponseCall = apiInterface.logIn(Utils.LOGIN_URL_LOCALHOST, logInRequest);
        logInResponseCall.enqueue(new Callback<logInResponse>() {
            @Override
            public void onResponse(Call<logInResponse> call, Response<logInResponse> response) {
                switch (response.code()){
                    case 200:
                        Log.d(TAG, "loginResponse: "  + response.body());
                        //saving the tokens and user info in sharedPreferences
                        setTokens(response);

                        //debug
                        Log.d(TAG, "shared Preferences: "+ sharedPreferences.getString("ACCESS_TOKEN","ACCESS TOKEN"));
                        //navigating to userFragment after login
                        NavHostFragment.findNavController(LogInFragment.this).navigate(LogInFragmentDirections.actionLogInFragmentToUserFragment());
                        break;
                    case 400:
                        Toast.makeText(getContext(),"login failed  ", Toast.LENGTH_SHORT).show();
                        break;
                    case 500:
                        Toast.makeText(getContext(),"server error ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<logInResponse> call, Throwable t) {
                Log.e(TAG, "Error occurred: " + t.getMessage());
                Toast.makeText(getContext(), "register request failed ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTokens(Response<logInResponse> response) {
        editor = sharedPreferences.edit();
        editor.putString("ACCESS_TOKEN", response.body().getTokens().getAccessToken());
        editor.putString("REFRESH_TOKEN", response.body().getTokens().getRefreshToken());
        editor.putString("USER_NAME", response.body().getUser().getName());
        editor.putString("USER_EMAIL", response.body().getUser().getEmail());
        editor.apply();
        Toast.makeText(getContext(),"Tokens Set, login successful ", Toast.LENGTH_SHORT).show();
    }


}