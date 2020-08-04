package com.badr.recipe_app.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.badr.recipe_app.Adapters.ingredientAdapter;
import com.badr.recipe_app.Model.ExtendedIngredient;
import com.badr.recipe_app.Model.Recipe;
import com.badr.recipe_app.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";
    private RecyclerView ingredientsRecyclerView;
    private ingredientAdapter ingredientAdapter;
    private List<ExtendedIngredient> extendedIngredientList;
    TextView recipeTitle, recipeScores;
    ImageView recipeImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        Recipe recipe = null;
        if(getArguments() != null){
            DetailsFragmentArgs args = DetailsFragmentArgs.fromBundle(getArguments());

            recipe = args.getRecipe();

            assert recipe != null;
            Log.d(TAG, "received args from HomeFragment : " + recipe.getTitle() );

            extendedIngredientList = recipe.getExtendedIngredients();
        }else {
            extendedIngredientList = new ArrayList<>();
        }

        assert recipe != null;
        setupUi(rootView, recipe);


        return rootView;
    }


    private void setupUi(View rootView, Recipe recipe){
        ingredientsRecyclerView = rootView.findViewById(R.id.ingredient_recyclerView);
        ingredientsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ingredientAdapter = new ingredientAdapter(extendedIngredientList, getContext());
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
        ingredientAdapter.notifyDataSetChanged();

        recipeTitle = rootView.findViewById(R.id.recipe_title);
        recipeScores = rootView.findViewById(R.id.recipe_scores);
        recipeImage = rootView.findViewById(R.id.recipe_image);

        recipeTitle.setText(recipe.getTitle());

        String scores = "Spoonacular Score: " + recipe.getSpoonacularScore() + "\nHealth Score: " + recipe.getHealthScore();
        recipeScores.setText(scores);

        if(recipe.getImage() != null){
            Glide.with(getContext()).load(recipe.getImage()).into(recipeImage);
        }else{
            Glide.with(getContext()).load(R.drawable.recipe_img_placeholder).into(recipeImage);
        }



    }


}