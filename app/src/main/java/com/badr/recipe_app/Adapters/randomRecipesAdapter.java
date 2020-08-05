package com.badr.recipe_app.Adapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.badr.recipe_app.Fragments.HomeFragmentDirections;
import com.badr.recipe_app.Model.RandomRecipe;
import com.badr.recipe_app.Model.Recipe;
import com.badr.recipe_app.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class randomRecipesAdapter extends RecyclerView.Adapter<randomRecipesAdapter.ViewHolder> {

    private Context context;
    private List<Recipe> randomRecipeList = new ArrayList<>();
    private static final String TAG = "randomRecipesAdapter";

    public randomRecipesAdapter(Context context) {
        this.context = context;

        //debug
        Log.d(TAG, "randomRecipeList size when constructor called: " + randomRecipeList.size());

    }

    public void setData(List<Recipe> recipes){
        //getting list os recipes
        this.randomRecipeList = recipes;
        notifyDataSetChanged();
        Log.d(TAG, "setData method recipes size -- randomRecipesList size: " + recipes.size()
                + "\t" + randomRecipeList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //getting current Recipe
        Recipe currentRecipe = randomRecipeList.get(position);
        //setting tile
        holder.recipeTitle.setText(currentRecipe.getTitle());

        //setting image with Glide
        if(currentRecipe.getImage() != null){
            Glide.with(context).load(currentRecipe.getImage()).fitCenter().into(holder.recipeImage);
        }else{
            Glide.with(context).load(R.drawable.recipe_img_placeholder).into(holder.recipeImage);
        }

    }

    @Override
    public int getItemCount() {
        return randomRecipeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recipeTitle;
        ImageView recipeImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //setting a click listener on the hole itemView
            itemView.setOnClickListener(this);

            recipeImage =  itemView.findViewById(R.id.recipe_image);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Recipe currentRecipe = randomRecipeList.get(position);

            Log.d(TAG, "on item clicked: " + currentRecipe.getTitle() + "\n" + currentRecipe.getSummary());

            //navigate to details fragment && passing currentRecipe object to be displayed
            //creating action with currentRecipe
            HomeFragmentDirections.ActionHomeFragmentToDetailsFragment action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(currentRecipe);
            Navigation.findNavController(v).navigate(action);
        }
    }
}
