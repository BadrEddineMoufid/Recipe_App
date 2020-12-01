package com.badr.recipe_app;

import android.view.View;

import com.badr.recipe_app.Model.searchRecipes.Result;


public interface itemClickListener {

    void onClick(View view, Result result);
}
