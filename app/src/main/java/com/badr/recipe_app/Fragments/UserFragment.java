 package com.badr.recipe_app.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.recipe_app.ApiClient;
import com.badr.recipe_app.ApiInterface;
import com.badr.recipe_app.Model.changePasswordPOST;
import com.badr.recipe_app.R;
import com.badr.recipe_app.Utils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private AlertDialog.Builder dialogBuilder;
    private ApiInterface apiInterface;
    private AlertDialog alertDialog;
    private static final String TAG = "UserFragment";
    private TextView userName, passwordErrorTextView, usernameMessageTextView;
    private EditText userNameInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        //checking if user is logged in

        if(!sharedPreferences.contains("ACCESS_TOKEN")){
            NavHostFragment.findNavController(UserFragment.this).navigate(UserFragmentDirections.actionUserFragmentToRegisterFragment());
            return rootView;
        }

        //displaying user's name to UI
        userName = rootView.findViewById(R.id.userFragment_userName);
        userName.setText(sharedPreferences.getString("USER_NAME", "USER_NAME"));



        //logout button
        Button logOutButton = rootView.findViewById(R.id.logout_button);
        logOutButton.setOnClickListener(v ->{
            logOut();
            Toast.makeText(getContext(),"Logged out ", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(UserFragment.this).navigate(UserFragmentDirections.actionUserFragmentToLogInFragment());
        });

        //change username button
        Button changeUserName = rootView.findViewById(R.id.change_username_button);
        changeUserName.setOnClickListener(V->{
            createAlertDialog(0);
        });

        //change password button
        Button changePasswordButton = rootView.findViewById(R.id.change_password_button);
        changePasswordButton.setOnClickListener(v->{
            createAlertDialog(1);
        });

        return rootView;
    }

    private void createAlertDialog(int id) {
        dialogBuilder = new AlertDialog.Builder(getContext());

        if(id == 0){
            View view = getLayoutInflater().inflate(R.layout.popup_change_name, null);
            userNameInput = view.findViewById(R.id.popup_username_input);
            Button saveButton = view.findViewById(R.id.popup_save_button);
            usernameMessageTextView = view.findViewById(R.id.popup_message_textview);

            dialogBuilder.setView(view);
            alertDialog = dialogBuilder.create();
            alertDialog.show();
            saveButton.setOnClickListener(v->{
                changeUserName(userNameInput.getText().toString());

            });
        }else if(id == 1){
            View view = getLayoutInflater().inflate(R.layout.popup_change_password, null);

            Button saveButton = view.findViewById(R.id.popup_password_save_button);
            EditText oldPassword = view.findViewById(R.id.popup_password_oldPassword);
            EditText newPassword = view.findViewById(R.id.popup_password_newPassword);
            //set error textView
            passwordErrorTextView = view.findViewById(R.id.popup_password_error_textview);
            dialogBuilder.setView(view);
            alertDialog = dialogBuilder.create();
            alertDialog.show();
            saveButton.setOnClickListener(v->{
                changePassword(oldPassword.getText().toString(), newPassword.getText().toString());
            });
        }

    }

    private void changePassword(String oldPassword, String newPassword) {
        //getting accessToken and setting post object
        changePasswordPOST changePasswordPOST = new changePasswordPOST(oldPassword, newPassword);
        String accessToken = "Bearer " + sharedPreferences.getString("ACCESS_TOKEN","ACCESS_TOKEN");

        Call<ResponseBody> changePassword = apiInterface.changePassword(Utils.CHANGE_PASSWORD_URL_LOCALHOST, accessToken, changePasswordPOST);
        changePassword.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(response.isSuccessful()){
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        Log.d(TAG, "change password response: " + jsonResponse.toString());
                        passwordErrorTextView.setVisibility(View.VISIBLE);
                        passwordErrorTextView.setText(jsonResponse.getString("msg"));
                        passwordErrorTextView.setTextColor(Color.parseColor("#0466c8"));


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        passwordErrorTextView.setVisibility(View.VISIBLE);
                        passwordErrorTextView.setText(jsonError.getString("error"));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "An ERROR OCCURRED", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void changeUserName(String newUserName) {

        if(newUserName.length() <= 6){
            Toast.makeText(getContext(), "input a valid username: > 6 chars", Toast.LENGTH_LONG).show();
            userNameInput.setError("username must be at least 6 chars");
        }else{
            String accessToken = "Bearer " + sharedPreferences.getString("ACCESS_TOKEN","ACCESS_TOKEN");

            Call<ResponseBody> changeUserName = apiInterface.changeUserName(Utils.CHANGE_USERNAME_URL_LOCALHOST, accessToken, newUserName);
            changeUserName.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            //debug
                            Log.d(TAG, "new username response: " + jsonObject.get("name").toString());

                            //updating sharedPreferences
                            updateSharedPreferences(jsonObject.get("name").toString());
                            //displaying new username
                            userName.setText(jsonObject.get("name").toString());

                            Toast.makeText(getContext(), "username changed successfully", Toast.LENGTH_SHORT).show();

                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "An ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        try {
                            JSONObject jsonError = new JSONObject(response.errorBody().string());
                            usernameMessageTextView.setVisibility(View.VISIBLE);
                            usernameMessageTextView.setText(jsonError.getString("error"));
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "An ERROR OCCURRED", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void updateSharedPreferences(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_NAME", name);
        editor.apply();
        alertDialog.cancel();
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