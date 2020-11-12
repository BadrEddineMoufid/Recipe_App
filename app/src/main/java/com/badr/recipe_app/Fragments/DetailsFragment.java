package com.badr.recipe_app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.recipe_app.Adapters.ingredientAdapter;
import com.badr.recipe_app.Adapters.instructionsAdapter;
import com.badr.recipe_app.Adapters.similarRecipesAdapter;
import com.badr.recipe_app.ApiClient;
import com.badr.recipe_app.ApiInterface;
import com.badr.recipe_app.Model.ExtendedIngredient;
import com.badr.recipe_app.Model.Recipe;
import com.badr.recipe_app.Model.Step;
import com.badr.recipe_app.Model.Tokens;
import com.badr.recipe_app.Model.favoriteRecipePOST;
import com.badr.recipe_app.Model.favoriteRecipeResponse;
import com.badr.recipe_app.Model.similarRecipe;
import com.badr.recipe_app.R;
import com.badr.recipe_app.Utils;
import com.badr.recipe_app.recyclerViewItemClickListener;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";
    private RecyclerView ingredientsRecyclerView;
    private ingredientAdapter ingredientAdapter;
    private List<ExtendedIngredient> extendedIngredientList;
    private TextView recipeTitle, recipeScores, summaryTextView;
    private ImageView recipeImage;
    private ImageButton addToFavoritesButton;
    private List<Step> stepList;
    private RecyclerView instructionRecyclerView;
    private instructionsAdapter instructionsAdapter;

    private List<similarRecipe> similarRecipeList;
    private RecyclerView similarRecipesRecyclerView;
    private similarRecipesAdapter similarRecipesAdapter;

    private SharedPreferences sharedPreferences;
    private ApiInterface apiInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //apiInterface instance
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);



        //getting recipe object from passed args
        Recipe recipe = null;
        if(getArguments() != null){

            DetailsFragmentArgs args = DetailsFragmentArgs.fromBundle(getArguments());

            recipe = args.getRecipe();

            assert recipe != null;
            Log.d(TAG, "received args from HomeFragment : " + recipe.getTitle() );

            //getting stepList and Ingredients from recipe object
            extendedIngredientList = recipe.getExtendedIngredients();

            if(recipe.getAnalyzedInstructions().size() == 0){
                stepList = new ArrayList<>();
            }else{
                stepList = recipe.getAnalyzedInstructions().get(0).getSteps();
            }


        }else {
            extendedIngredientList = new ArrayList<>();
            stepList = new ArrayList<>();
            Snackbar.make(rootView, "an error occurred ", Snackbar.LENGTH_LONG).show();
        }

        //setting the ui
        assert recipe != null;
        setupUi(rootView, recipe);

        //getting similar recipes
        getSimilarRecipes(rootView, recipe);

        return rootView;
    }


    private void setupUi(View rootView, Recipe recipe){

        //setting up ingredientsRecyclerView
        ingredientsRecyclerView = rootView.findViewById(R.id.ingredient_recyclerView);
        ingredientsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //setting up ingredientAdapter
        ingredientAdapter = new ingredientAdapter(extendedIngredientList, getContext());
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
        ingredientAdapter.notifyDataSetChanged();

        //setting up instructionsRecyclerView
        instructionRecyclerView = rootView.findViewById(R.id.instructions_recyclerView);
        instructionRecyclerView.setHasFixedSize(true);
        instructionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //setting up instructionsAdapter
        instructionsAdapter = new instructionsAdapter(getContext(),stepList);
        instructionRecyclerView.setAdapter(instructionsAdapter);
        instructionsAdapter.notifyDataSetChanged();

        //finding views
        recipeTitle = rootView.findViewById(R.id.recipe_title);
        recipeScores = rootView.findViewById(R.id.recipe_scores);
        recipeImage = rootView.findViewById(R.id.recipe_image);
        summaryTextView = rootView.findViewById(R.id.summary_textView);
        addToFavoritesButton = rootView.findViewById(R.id.add_to_favorites_button);

        //setting texts
        recipeTitle.setText(recipe.getTitle());
        String scores = "Spoonacular Score: " + recipe.getSpoonacularScore() + "\nHealth Score: " + recipe.getHealthScore();
        recipeScores.setText(scores);

        //clean the summary from html tags before displaying it
        if(recipe.getSummary() != null){
            summaryTextView.setText(cleanString(recipe.getSummary()));
        }else{
            summaryTextView.setText("no summary provided");
        }

        //click listener
        addToFavoritesButton.setOnClickListener(v ->{
            addToFavorites(recipe);
        });


        //setting image
        if(recipe.getImage() != null){
            Glide.with(getContext()).load(recipe.getImage()).into(recipeImage);
        }else{
            Glide.with(getContext()).load(R.drawable.recipe_img_placeholder).into(recipeImage);
        }



    }

    private void addToFavorites(Recipe recipe){
        //retrieving access token
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", "ACCESS_TOKEN");
        favoriteRecipePOST favoriteRecipePOST = new favoriteRecipePOST(recipe.getId(), recipe.getTitle(), recipe.getImage());

        Call<favoriteRecipeResponse> addFavoriteRecipe = apiInterface.addFavoriteRecipe(Utils.FAVORITE_RECIPES_URL_LOCALHOST,
                "Bearer " + accessToken, favoriteRecipePOST );

        addFavoriteRecipe.enqueue(new Callback<favoriteRecipeResponse>() {
            @Override
            public void onResponse(Call<favoriteRecipeResponse> call, Response<favoriteRecipeResponse> response) {
                switch (response.code()){
                    case 200:
                        //Log.d(TAG, "add to favorites response: "+ response.body().toString());
                        Toast.makeText(getContext(), "Recipe Added to favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(getContext(), "You are not logged in", Toast.LENGTH_SHORT).show();
                        break;
                    case 500:
                        Toast.makeText(getContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<favoriteRecipeResponse> call, Throwable t) {
                Log.e(TAG, "add to favorites failure : " + t.getMessage() + t.toString());
                Toast.makeText(getContext(), "An ERROR OCCURRED ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSimilarRecipes(View view, Recipe recipe){
        similarRecipesRecyclerView = view.findViewById(R.id.similar_recipes_recyclerView);

        similarRecipeList = new ArrayList<>();

        similarRecipesRecyclerView.setHasFixedSize(true);
        similarRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewItemClickListener listener = (similarRecipe)->{
            Toast.makeText(getContext(), "item clicked; " + similarRecipe.getTitle(), Toast.LENGTH_SHORT).show();
            getRecipe(similarRecipe, view);
        };

        similarRecipesAdapter = new similarRecipesAdapter(getContext(), listener);



        String url = "https://api.spoonacular.com/recipes/" + recipe.getId() + "/similar";

        //fetching similarRecipes
        Call<List<similarRecipe>> getSimilarRecipes = apiInterface.getSimilarRecipes(url, Utils.API_KEY);
        getSimilarRecipes.enqueue(new Callback<List<similarRecipe>>() {
            @Override
            public void onResponse(Call<List<similarRecipe>> call, Response<List<similarRecipe>> response) {


                similarRecipeList = response.body();
                similarRecipesAdapter.setData(similarRecipeList);
                similarRecipesRecyclerView.setAdapter(similarRecipesAdapter);
                similarRecipesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<similarRecipe>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "request failed ", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void getRecipe(similarRecipe similarRecipe, View rootView) {
        Call<Recipe> getRecipe = apiInterface.getRecipe(similarRecipe.getId(), Utils.API_KEY);

        getRecipe.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()){
                    DetailsFragmentDirections.ActionDetailsFragmentSelf action = DetailsFragmentDirections.actionDetailsFragmentSelf(response.body());
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

    private String cleanString(String text){
        //remove all html tags
        String strRegEx = "<[^>]*>";
        String result = text.replaceAll(strRegEx, " ");

        //insert new line after dot
        result = result.replaceAll("\\.\\s?","\\.\n");

        //debug
        //Log.d(TAG, "clean string : " + result);

        return result;
    }


}