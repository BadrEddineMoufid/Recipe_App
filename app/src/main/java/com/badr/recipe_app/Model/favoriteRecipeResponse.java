package com.badr.recipe_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class favoriteRecipeResponse {
    @SerializedName("favorite recipe")
    @Expose
    private List<favoriteRecipe> favoriteRecipe = null;

    public List<favoriteRecipe> getFavoriteRecipe() {
        return favoriteRecipe;
    }

    public void setFavoriteRecipe(List<favoriteRecipe> favoriteRecipe) {
        this.favoriteRecipe = favoriteRecipe;
    }

    @Override
    public String toString() {
        return "favoriteRecipeResponse{" +
                "favoriteRecipe=" + favoriteRecipe +
                '}';
    }
}
