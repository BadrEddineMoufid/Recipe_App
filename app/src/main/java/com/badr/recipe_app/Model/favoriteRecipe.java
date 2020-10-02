package com.badr.recipe_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class favoriteRecipe implements Serializable {
    @SerializedName("recipe_id")
    @Expose
    private int recipeId;
    @SerializedName("recipe_title")
    @Expose
    private String recipeTitle;
    @SerializedName("recipe_image_url")
    @Expose
    private String recipeImageUrl;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeImageUrl() {
        return recipeImageUrl;
    }

    public void setRecipeImageUrl(String recipeImageUrl) {
        this.recipeImageUrl = recipeImageUrl;
    }

    @Override
    public String toString() {
        return "favoriteRecipe{" +
                "recipeId=" + recipeId +
                ", recipeTitle='" + recipeTitle + '\'' +
                ", recipeImageUrl='" + recipeImageUrl + '\'' +
                '}';
    }
}
