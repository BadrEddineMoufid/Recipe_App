package com.badr.recipe_app;


import com.badr.recipe_app.Model.RandomRecipe;
import com.badr.recipe_app.Model.searchRecipes;
import com.badr.recipe_app.Model.similarRecipe;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    /**
     * @param query your search query
     * @param apiKey your apikey
     * @param number number of recipes to get
     * */

    @GET("/recipes/search")
    Call<searchRecipes> searchForRecipe(@Query("query") String query, @Query("number") int number, @Query("apiKey") String apiKey);


    /**
     * @param number number of recipes to get
     * @param apiKey your api key
     * */
    @GET("/recipes/random")
    Call<RandomRecipe> getRandomRecipes(@Query("number") int number, @Query("apiKey") String apiKey);

    /**
    * @param number number of recipes to get
    * @param apiKey your api key
    * @param tags tag to filter recipes
    * */
    @GET("/recipes/random")
    Call<RandomRecipe> getRandomRecipesWithTags(@Query("number")int number, @Query("apiKey") String apiKey, @Query("tags") String tags);

    @GET()
    Call<List<similarRecipe>> getSimilarRecipes(@Url String url, @Query("apiKey") String apiKey);
}
