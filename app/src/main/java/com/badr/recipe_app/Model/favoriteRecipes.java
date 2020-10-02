package com.badr.recipe_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class favoriteRecipes {
    @SerializedName("favoriteRecipes")
    @Expose
    private List<favoriteRecipe> favoriteRecipes = null;

    public List<favoriteRecipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<favoriteRecipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }
}
