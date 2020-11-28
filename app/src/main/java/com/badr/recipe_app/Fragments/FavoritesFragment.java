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
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.badr.recipe_app.sessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.badr.recipe_app.Utils.API_KEY;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private favoriteRecipesAdapter favoriteRecipesAdapter;
    private sessionManager sessionManager;
    private ApiInterface apiInterface;
    private RelativeLayout errorLayout;
    private Button errorLayoutButton;
    private TextView errorLayoutTextView;
    private static final String TAG = "FavoritesFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new sessionManager(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        //setting error layout
        errorLayout = rootView.findViewById(R.id.error_layout);
        errorLayoutButton = rootView.findViewById(R.id.error_layout_button);
        errorLayoutTextView = rootView.findViewById(R.id.error_layout_textview);

        if(!sessionManager.isLoggedIn()){
            Toast.makeText(getContext(), "You are not logged in ", Toast.LENGTH_SHORT).show();

            //displaying error
            errorLayout.setVisibility(View.VISIBLE);
            errorLayoutTextView.setText("Log In to view favorite recipes");
            errorLayoutButton.setOnClickListener(v->{
                NavHostFragment.findNavController(FavoritesFragment.this).navigate(FavoritesFragmentDirections.actionFavoritesFragmentToUserFragment());

            });
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
        String accessToken = "Bearer " + sessionManager.getAccessToken();

        Call<favoriteRecipes> getFavoriteRecipes = apiInterface.getFavoriteRecipes(Utils.FAVORITE_RECIPES_URL_LOCALHOST, accessToken );

        getFavoriteRecipes.enqueue(new Callback<favoriteRecipes>() {
            @Override
            public void onResponse(Call<favoriteRecipes> call, Response<favoriteRecipes> response) {
                switch (response.code()){
                    case 200:
                        Log.d(TAG, "favorite recipes response: " + response.body().getFavoriteRecipes().toString() );
                        recyclerView.setAdapter(favoriteRecipesAdapter);
                        favoriteRecipesAdapter.setData(response.body().getFavoriteRecipes());
                        if(response.body().getFavoriteRecipes().size() == 0){

                            recyclerView.setVisibility(View.GONE);
                            errorLayout.setVisibility(View.VISIBLE);
                            errorLayoutButton.setVisibility(View.GONE);
                            errorLayoutTextView.setText("No favorite recipes Yet!!!");

                        }
                        favoriteRecipesAdapter.notifyDataSetChanged();
                        break;
                    case 403:
                        Log.d(TAG, "access token revoked");
                        Toast.makeText(getContext(), "Access Token revoked log in", Toast.LENGTH_SHORT).show();

                        sessionManager.logOut();
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

}



