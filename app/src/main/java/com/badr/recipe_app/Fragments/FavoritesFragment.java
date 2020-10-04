package com.badr.recipe_app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.badr.recipe_app.Adapters.favoriteRecipesAdapter;
import com.badr.recipe_app.ApiClient;
import com.badr.recipe_app.ApiInterface;
import com.badr.recipe_app.Model.favoriteRecipes;
import com.badr.recipe_app.R;
import com.badr.recipe_app.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private favoriteRecipesAdapter favoriteRecipesAdapter;
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
            Toast.makeText(getContext(), "You are not logged in ", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(FavoritesFragment.this).navigate(FavoritesFragmentDirections.actionFavoritesFragmentToUserFragment());
            return rootView;
        }

        recyclerView = rootView.findViewById(R.id.favoriteRecipes_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        favoriteRecipesAdapter = new favoriteRecipesAdapter(getContext());

        getFavoriteRecipes();
        return rootView;
    }
    private void getFavoriteRecipes(){
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", "ACCESS_TOKEN");

        Call<favoriteRecipes> getFavoriteRecipes = apiInterface.getFavoriteRecipes(Utils.FAVORITE_RECIPES_URL_LOCALHOST,
                "Bearer " + accessToken );

        getFavoriteRecipes.enqueue(new Callback<favoriteRecipes>() {
            @Override
            public void onResponse(Call<favoriteRecipes> call, Response<favoriteRecipes> response) {
                switch (response.code()){
                    case 200:
                        Log.d(TAG, "favorite recipes response: " + response.body().getFavoriteRecipes().toString() );
                        recyclerView.setAdapter(favoriteRecipesAdapter);
                        favoriteRecipesAdapter.setData(response.body().getFavoriteRecipes());
                        favoriteRecipesAdapter.notifyDataSetChanged();
                        break;
                    case 403:
                        Log.d(TAG, "access token revoked");
                        Toast.makeText(getContext(), "Access Token revoked log in", Toast.LENGTH_SHORT).show();
                        logOut();
                        NavHostFragment.findNavController(FavoritesFragment.this).navigate(FavoritesFragmentDirections.actionFavoritesFragmentToUserFragment());
                        break;
                    case 500:
                        Toast.makeText(getContext(),"Server Error ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<favoriteRecipes> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(),"Request failed ", Toast.LENGTH_SHORT).show();
            }
        });
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



