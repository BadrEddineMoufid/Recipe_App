package com.badr.recipe_app.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.badr.recipe_app.Model.favoriteRecipe;
import com.badr.recipe_app.Model.favoriteRecipes;
import com.badr.recipe_app.R;
import com.badr.recipe_app.recyclerViewClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class favoriteRecipesAdapter extends RecyclerView.Adapter<favoriteRecipesAdapter.ViewHolder> {
    private List<favoriteRecipe> favoriteRecipesList = new ArrayList<>();
    private Context context;
    private recyclerViewClickListener mListener;
    private static final String TAG = "favoriteRecipesAdapter";

    public favoriteRecipesAdapter(Context context, recyclerViewClickListener listener) {
        mListener = listener;
        this.context = context;
    }

    public void setData(List<favoriteRecipe> favoriteRecipesList){
        this.favoriteRecipesList = favoriteRecipesList;
        notifyDataSetChanged();

        //debug
        Log.d(TAG, "favoriteRecipesList size: " + this.favoriteRecipesList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
       return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        favoriteRecipe currentFavoriteRecipe = favoriteRecipesList.get(position);

        holder.recipeTitle.setText(currentFavoriteRecipe.getRecipeTitle());

        Glide.with(context).load(currentFavoriteRecipe.getRecipeImageUrl()).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return favoriteRecipesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recipeImage;
        TextView recipeTitle;
        private recyclerViewClickListener mListener;

        public ViewHolder(@NonNull View itemView, recyclerViewClickListener Listener) {
            super(itemView);

            mListener = Listener;

            itemView.setOnClickListener(this);

            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
        }

        @Override
        public void onClick(View v) {

            favoriteRecipe currentFavoriteRecipe =  favoriteRecipesList.get(getAdapterPosition());

            mListener.onClick(itemView, currentFavoriteRecipe);
        }
    }
}



