package com.badr.recipe_app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.badr.recipe_app.Adapters.randomRecipesAdapter;
import com.badr.recipe_app.Adapters.recyclerViewAdapter2;
import com.badr.recipe_app.ApiClient;
import com.badr.recipe_app.ApiInterface;
import com.badr.recipe_app.Model.RandomRecipe;
import com.badr.recipe_app.Model.Recipe;
import com.badr.recipe_app.Model.searchRecipes;
import com.badr.recipe_app.R;
import com.badr.recipe_app.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "HomeFragment";
    ApiInterface apiInterface;
    private List<Recipe> recipeList;
    private RecyclerView randomRecipesRecyclerView;
    private randomRecipesAdapter randomRecipesAdapter;
    private RecyclerView recyclerView2;
    private recyclerViewAdapter2 recyclerViewAdapter2;
    private List<Recipe> recipeList2;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        randomRecipesRecyclerView = rootView.findViewById(R.id.random_recipes_recyclerView);
        randomRecipesRecyclerView.setHasFixedSize(true);
        randomRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        //initialize the list
        recipeList = new ArrayList<>();
        //instantiate adapter
        randomRecipesAdapter = new randomRecipesAdapter(getActivity());
        //call API
        getRandomRecipes();

        //setting spinner and spinner adapter
        Spinner spinner = rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.meal_types, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        //setting recyclerView2
        recyclerView2 = rootView.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recipeList2 = new ArrayList<>();
        recyclerViewAdapter2 = new recyclerViewAdapter2(getActivity());



        return rootView;
    }

    private void getRandomRecipes(){
        Call<RandomRecipe> getRandom = apiInterface.getRandomRecipes(5 , Utils.API_KEY);
        getRandom.enqueue(new Callback<RandomRecipe>() {
            @Override
            public void onResponse(Call<RandomRecipe> call, Response<RandomRecipe> response) {
                //debug
                Log.d(TAG, "on response : " + response.body());
                Log.d(TAG, "recipeList  size : " + recipeList.size());

                //logging used API-Quota
                Log.d(TAG, "quota used today in total: " + response.headers().get("X-API-Quota-Used") +
                        " quota used by this request: " + response.headers().get("X-API-Quota-Request"));

                //get list from response body
                assert response.body() != null;
                recipeList = response.body().getRecipes();

                //passing the list to adapter && setting the adapter to recyclerView && notifyDataSetChanged
                randomRecipesAdapter.setData(recipeList);
                randomRecipesRecyclerView.setAdapter(randomRecipesAdapter);
                randomRecipesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<RandomRecipe> call, Throwable t) {
                Log.e(TAG, "on failure : " + t.getMessage() + t.toString());
            }
        });


    }

    private void getRandomRecipesWithTags(String tags){
        int number = 10;

        Call<RandomRecipe> getRandomRecipesWithTags = apiInterface.getRandomRecipesWithTags(number, Utils.API_KEY, tags);

        getRandomRecipesWithTags.enqueue(new Callback<RandomRecipe>() {
            @Override
            public void onResponse(Call<RandomRecipe> call, Response<RandomRecipe> response) {
                Log.d(TAG, "recipes with tags: " + response.body());

                assert response.body() != null;
                recipeList2 = response.body().getRecipes();
                recyclerViewAdapter2.setData(recipeList2);
                recyclerView2.setAdapter(recyclerViewAdapter2);
                recyclerViewAdapter2.notifyDataSetChanged();

                Log.d(TAG, "quota used today in total: " + response.headers().get("X-API-Quota-Used") +
                        "\nquota used by this request: " + response.headers().get("X-API-Quota-Request"));

            }

            @Override
            public void onFailure(Call<RandomRecipe> call, Throwable t) {
                Log.e(TAG, "on failure : " + t.getMessage() + t.toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(), "spinner item clicked: " + text , Toast.LENGTH_LONG).show();

        //calling API
        getRandomRecipesWithTags(text);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}