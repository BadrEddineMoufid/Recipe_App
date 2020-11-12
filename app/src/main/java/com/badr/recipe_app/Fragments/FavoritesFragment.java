package com.badr.recipe_app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.recipe_app.Adapters.favoriteRecipesAdapter;
import com.badr.recipe_app.ApiClient;
import com.badr.recipe_app.ApiInterface;
import com.badr.recipe_app.Model.Recipe;
import com.badr.recipe_app.Model.favoriteRecipe;
import com.badr.recipe_app.Model.favoriteRecipes;
import com.badr.recipe_app.R;
import com.badr.recipe_app.Utils;
import com.badr.recipe_app.recyclerViewClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.badr.recipe_app.Utils.API_KEY;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView listEmpty;
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

        //ui stuff
        setUi(rootView);

        getFavoriteRecipes();

        return rootView;
    }

    private void setUi(View rootView){
        recyclerView = rootView.findViewById(R.id.favoriteRecipes_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerViewClickListener listener = (view, favoriteRecipe) ->{

            Toast.makeText(getContext(), "favorite recipe item clicked " , Toast.LENGTH_SHORT).show();
            getRecipe( favoriteRecipe, rootView);
        };

        favoriteRecipesAdapter = new favoriteRecipesAdapter(getContext(), listener);

        listEmpty = rootView.findViewById(R.id.list_empty);
    }

    private void getRecipe(favoriteRecipe favoriteRecipe, View rootView) {

        Call<Recipe> getRecipe = apiInterface.getRecipe(favoriteRecipe.getRecipeId(), Utils.API_KEY);
        getRecipe.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()){
                    FavoritesFragmentDirections.ActionFavoritesFragmentToDetailsFragment action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(response.body());
                    Navigation.findNavController(rootView).navigate(action);
                }else{
                    Toast.makeText(getContext(), "request failed status: " + response.code() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "request failed ", Toast.LENGTH_SHORT).show();
            }
        });

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
                        if(response.body().getFavoriteRecipes().size() == 0){
                            listEmpty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
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
                Log.e(TAG, t.toString());

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



