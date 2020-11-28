package com.badr.recipe_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class sessionManager {
    private SharedPreferences sharedPreferences;
    private Context context;
    private Editor editor;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "session";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";


    public sessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void logIn(String userName, String email, String accessToken, String refreshToken){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER_NAME, userName);
        editor.putString(USER_EMAIL, email);
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.putString(REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }



    public void logOut(){
        editor.clear();
        editor.apply();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(USER_EMAIL, sharedPreferences.getString(USER_EMAIL, null));
        user.put(USER_NAME, sharedPreferences.getString(USER_NAME, null));
        user.put(ACCESS_TOKEN, sharedPreferences.getString(ACCESS_TOKEN, null));
        user.put(REFRESH_TOKEN, sharedPreferences.getString(REFRESH_TOKEN, null));
        return user;
    }

    public String getAccessToken(){
        return sharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public String getRefreshToken(){
        return sharedPreferences.getString(REFRESH_TOKEN, null);
    }

    public String getUserName(){
        return sharedPreferences.getString(USER_NAME, null);
    }

    public void setUserName(String userName){
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

}
