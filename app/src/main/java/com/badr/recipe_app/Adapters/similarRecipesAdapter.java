package com.badr.recipe_app.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.badr.recipe_app.Fragments.DetailsFragment;
import com.badr.recipe_app.Fragments.DetailsFragmentArgs;
import com.badr.recipe_app.Fragments.DetailsFragmentDirections;
import com.badr.recipe_app.Model.similarRecipe;
import com.badr.recipe_app.R;
import com.badr.recipe_app.recyclerViewClickListener;
import com.badr.recipe_app.recyclerViewItemClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class similarRecipesAdapter extends RecyclerView.Adapter<similarRecipesAdapter.ViewHolder> {
    private List<similarRecipe> similarRecipeList = new ArrayList<>();
    private Context context;
    private recyclerViewItemClickListener mListener;

    private static final String TAG = "similarRecipesAdapter";

    public similarRecipesAdapter(Context context, recyclerViewItemClickListener listener) {
        this.context = context;
        this.mListener = listener;

    }

    public void setData(List<similarRecipe> similarRecipeList){
        this.similarRecipeList = similarRecipeList;
        notifyDataSetChanged();


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating layout and returning new ViewHolder object
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);

        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //getting current similarRecipe from list
        similarRecipe currentSimilarRecipe = similarRecipeList.get(position);

        //setting title
        holder.recipeTitle.setText(currentSimilarRecipe.getTitle());

        //setting image
        //schema: spoonacular.com/recipeImages/{ID}-{SIZE}.{TYPE}
        String imageUrl = "https://spoonacular.com/recipeImages/"+ currentSimilarRecipe.getId() + "-" + "556x370."
                +currentSimilarRecipe.getImageType();

        Glide.with(context).load(imageUrl).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return similarRecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recipeTitle;
        ImageView recipeImage;
        private recyclerViewItemClickListener mListener;

        public ViewHolder(@NonNull View itemView, recyclerViewItemClickListener listener) {
            super(itemView);
            this.mListener = listener;

            itemView.setOnClickListener(this);

            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
        }

        @Override
        public void onClick(View v) {
            similarRecipe currentSimilarRecipe = similarRecipeList.get(getAdapterPosition());
            mListener.onClick(currentSimilarRecipe);

        }
    }
}
