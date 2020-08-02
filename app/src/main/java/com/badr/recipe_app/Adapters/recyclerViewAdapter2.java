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

import com.badr.recipe_app.Model.Recipe;
import com.badr.recipe_app.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class recyclerViewAdapter2 extends RecyclerView.Adapter<recyclerViewAdapter2.ViewHolder> {
    private Context context;
    private List<Recipe> recipeList = new ArrayList<>();
    private static final String TAG = "recyclerViewAdapter2";

    public recyclerViewAdapter2(Context context) {
        this.context = context;
    }

    public void setData(List<Recipe> recipeList){
        this.recipeList = recipeList;
        notifyDataSetChanged();

        //debug
        Log.d(TAG, "recipeList size: " + recipeList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating layout
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card_item, parent, false);

       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //getting current Recipe
        Recipe currentRecipe = recipeList.get(position);

        //setting title
        holder.recipeCardTitle.setText(currentRecipe.getTitle());

        //setting image with glide
        if(currentRecipe.getImage() != null){
            Glide.with(context).load(currentRecipe.getImage()).into(holder.recipeCardImage);
        }else{
            Glide.with(context).load(R.drawable.recipe_img_placeholder).into(holder.recipeCardImage);
        }


    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeCardTitle;
        ImageView recipeCardImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //setting a click listener on the hole itemView
            itemView.setOnClickListener(this);

            recipeCardImage = itemView.findViewById(R.id.recipe_card_image);
            recipeCardTitle = itemView.findViewById(R.id.recipe_card_title);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Recipe currentRecipe = recipeList.get(pos);

            //debug
            Log.d(TAG, "on item clicked: " + currentRecipe.getTitle() + "\n" + currentRecipe.getSummary());
        }
    }
}
