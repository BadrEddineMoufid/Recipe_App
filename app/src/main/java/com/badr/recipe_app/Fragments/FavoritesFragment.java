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
import android.widget.Toast;

import com.badr.recipe_app.ApiClient;
import com.badr.recipe_app.ApiInterface;
import com.badr.recipe_app.Model.favoriteRecipes;
import com.badr.recipe_app.R;
import com.badr.recipe_app.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private ApiInterface apiInterface;
    private static final String TAG = "FavoritesFragment";

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
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        if(!sharedPreferences.contains("ACCESS_TOKEN")){
            NavHostFragment.findNavController(FavoritesFragment.this).navigate(FavoritesFragmentDirections.actionFavoritesFragmentToUserFragment());
            return rootView;
        }
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", "ACCESS_TOKEN");

        Call<favoriteRecipes> getFavoriteRecipes = apiInterface.getFavoriteRecipes(Utils.FAVORITE_RECIPES_URL_LOCALHOST,
               "Bearer " + accessToken );

        getFavoriteRecipes.enqueue(new Callback<favoriteRecipes>() {
            @Override
            public void onResponse(Call<favoriteRecipes> call, Response<favoriteRecipes> response) {
                switch (response.code()){
                    case 200:
                        Log.d(TAG, "favorite recipes response: " + response.body().getFavoriteRecipes().toString() );
                        break;
                    case 403:
                        Log.d(TAG, "access token revoked");
                        Toast.makeText(getContext(), "Access Token revoked log in", Toast.LENGTH_SHORT).show();
                        logOut();
                        break;
                    case 500:
                        Toast.makeText(getContext(),"Server Error try again later", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<favoriteRecipes> call, Throwable t) {
                Log.e(TAG, "Request Failed: " + t.getMessage() );
                t.printStackTrace();
                Toast.makeText(getContext(),"Request failed ", Toast.LENGTH_SHORT).show();
            }
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