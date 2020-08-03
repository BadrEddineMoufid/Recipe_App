package com.badr.recipe_app.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badr.recipe_app.Model.Recipe;
import com.badr.recipe_app.R;

public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        if(getArguments() != null){
            DetailsFragmentArgs args = DetailsFragmentArgs.fromBundle(getArguments());

            Recipe recipe = args.getRecipe();
            Log.d(TAG, "received args from HomeFragment : " + recipe.getTitle() );
        }


        return rootView;
    }



}