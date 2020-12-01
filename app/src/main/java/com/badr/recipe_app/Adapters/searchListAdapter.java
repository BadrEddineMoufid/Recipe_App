package com.badr.recipe_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.badr.recipe_app.Model.searchRecipes.Result;
import com.badr.recipe_app.R;
import com.badr.recipe_app.itemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

public class searchListAdapter extends RecyclerView.Adapter<searchListAdapter.ViewHolder> {
    private Context context;
    private List<Result> searchRecipesResult;
    private itemClickListener mListener;
    private static final String TAG = "searchListAdapter";


    public searchListAdapter(Context context, itemClickListener listener){
        this.context = context;
        this.mListener = listener;
    }
    public void setData(List<Result> results){
        this.searchRecipesResult = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result currentResult = searchRecipesResult.get(position);
        holder.recipeTitle.setText(currentResult.getTitle());

        if(currentResult.getImage() != null){
            //setting image

            String imageUrl ="https://spoonacular.com/recipeImages/" + currentResult.getImage();
            Glide.with(context).load(imageUrl).centerCrop().into(holder.recipeImage);
        }else{
            Glide.with(context).load(R.drawable.recipe_img_placeholder).into(holder.recipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return searchRecipesResult.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recipeTitle;
        ImageView recipeImage;
        private itemClickListener mListener;

        public ViewHolder(@NonNull View itemView, itemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mListener = listener;

            recipeImage =  itemView.findViewById(R.id.recipe_image);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
        }

        @Override
        public void onClick(View v) {
            //get the current result from list of results and pass it to call back
            Result currentResult = searchRecipesResult.get(getAdapterPosition());
            mListener.onClick(v, currentResult);
        }
    }
}
