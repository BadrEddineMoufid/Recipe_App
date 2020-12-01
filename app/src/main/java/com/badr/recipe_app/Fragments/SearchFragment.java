package com.badr.recipe_app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.recipe_app.Adapters.searchListAdapter;
import com.badr.recipe_app.ApiClient;
import com.badr.recipe_app.ApiInterface;
import com.badr.recipe_app.Model.Recipe;
import com.badr.recipe_app.Model.favoriteRecipe;
import com.badr.recipe_app.Model.searchRecipes;
import com.badr.recipe_app.R;
import com.badr.recipe_app.Utils;
import com.badr.recipe_app.itemClickListener;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    private ApiInterface apiInterface;
    private EditText searchInput;
    private RecyclerView recyclerView;
    private searchListAdapter searchListAdapter;
    private TextView listEmptyTextView;
    private static final String TAG = "SearchFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        listEmptyTextView = rootView.findViewById(R.id.search_fragment_list_empty_textView);
        searchInput = rootView.findViewById(R.id.search_recipe_input);
        recyclerView = rootView.findViewById(R.id.search_fragment_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        itemClickListener listener = (v, result)->{
            getRecipe(result.getId(), rootView);
        };

        searchListAdapter = new searchListAdapter(getContext(), listener);

        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                Log.d(TAG,"Enter pressed");
                searchForRecipes(v.getText().toString());
            }
            return false;
        });


        return rootView;
    }

    private void searchForRecipes(String query){
        Call<searchRecipes> searchRecipesCall = apiInterface.searchForRecipe(query,10, Utils.API_KEY);
        searchRecipesCall.enqueue(new Callback<searchRecipes>() {
            @Override
            public void onResponse(Call<searchRecipes> call, Response<searchRecipes> response) {
                if(response.isSuccessful()){
                    listEmptyTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    searchListAdapter.setData(response.body().getResults());
                    recyclerView.setAdapter(searchListAdapter);
                    if(response.body().getResults().size() == 0){
                        recyclerView.setVisibility(View.GONE);
                        listEmptyTextView.setVisibility(View.VISIBLE);
                        listEmptyTextView.setText(R.string.no_match_found);
                    }

                    searchListAdapter.notifyDataSetChanged();
                }else{
                    try {
                        Log.e(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<searchRecipes> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void getRecipe(int recipeId, View rootView) {

        Call<Recipe> getRecipe = apiInterface.getRecipe(recipeId, Utils.API_KEY);
        getRecipe.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()){
                    SearchFragmentDirections.ActionSearchFragmentToDetailsFragment action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(response.body());
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

}