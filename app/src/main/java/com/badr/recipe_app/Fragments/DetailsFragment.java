package com.badr.recipe_app.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.badr.recipe_app.Adapters.ingredientAdapter;
import com.badr.recipe_app.Adapters.instructionsAdapter;
import com.badr.recipe_app.Model.ExtendedIngredient;
import com.badr.recipe_app.Model.Recipe;
import com.badr.recipe_app.Model.Step;
import com.badr.recipe_app.R;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import me.saket.bettermovementmethod.BetterLinkMovementMethod;

public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";
    private RecyclerView ingredientsRecyclerView;
    private ingredientAdapter ingredientAdapter;
    private List<ExtendedIngredient> extendedIngredientList;
    TextView recipeTitle, recipeScores, summaryTextView;
    ImageView recipeImage;
    private List<Step> stepList;
    private RecyclerView instructionRecyclerView;
    private instructionsAdapter instructionsAdapter;

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
            stepList = recipe.getAnalyzedInstructions().get(0).getSteps();
        }else {
            extendedIngredientList = new ArrayList<>();
            stepList = new ArrayList<>();
            Snackbar.make(rootView, "an error occurred check logs", Snackbar.LENGTH_LONG).show();
        }

        assert recipe != null;
        setupUi(rootView, recipe);


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

        //setting texts
        recipeTitle.setText(recipe.getTitle());
        String scores = "Spoonacular Score: " + recipe.getSpoonacularScore() + "\nHealth Score: " + recipe.getHealthScore();
        recipeScores.setText(scores);

        //clean the summary from html tags before displaying it
        summaryTextView.setText(cleanString(recipe.getSummary()));




        //setting image
        if(recipe.getImage() != null){
            Glide.with(getContext()).load(recipe.getImage()).into(recipeImage);
        }else{
            Glide.with(getContext()).load(R.drawable.recipe_img_placeholder).into(recipeImage);
        }



    }

    private String cleanString(String text){
        //remove all html tags
        String strRegEx = "<[^>]*>";
        String result = text.replaceAll(strRegEx, " ");

        //go to another line after dot
        result = result.replaceAll("\\.\\s?","\\.\n");

        //debug
        Log.d(TAG, "clean string : " + result);

        return result;
    }


}