package com.badr.recipe_app.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.badr.recipe_app.Model.ExtendedIngredient;
import com.badr.recipe_app.R;

import java.util.List;

public class ingredientAdapter extends RecyclerView.Adapter<ingredientAdapter.ViewHolder>{

    private List<ExtendedIngredient> extendedIngredientList;
    Context context;
    private static final String TAG = "ingredientAdapter";

    public ingredientAdapter(List<ExtendedIngredient> extendedIngredientList, Context context) {
        this.extendedIngredientList = extendedIngredientList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //getting current ingredient
        ExtendedIngredient currentIngredient = extendedIngredientList.get(position);

        String amount = round(currentIngredient.getAmount(), 1) + " ";
        if(currentIngredient.getUnit() != null){
            amount += currentIngredient.getUnit()  ;
        }else{
            amount.concat(currentIngredient.getMeasures().getMetric().getUnitLong());
        }


        //setting data to view
        holder.ingredientName.setText(cleanIngredientName(currentIngredient.getName() ));
        holder.ingredientAmount.setText(amount);


    }

    @Override
    public int getItemCount() {
        return extendedIngredientList.size();
    }

    private String cleanIngredientName(String name){

        StringBuilder sb = new StringBuilder(name);

        int i = 0;
        //getting the index of SPACE char after counting 20 characters and checking it's not -1
        //if true then replace that space char with a break line char
        //if -1 then string doesn't contain any spaces after 20 chars
        while ( (i = sb.indexOf(" ", i + 20)) != -1) {
            sb.replace(i, i + 1, "\n");

            //debug shiit
            //Log.d(TAG,"sb and index : "  + sb.toString() + " " + i);
        }

        //debug shit
        //Log.d(TAG,"sb: "  +sb.toString());

        return sb.toString();
    }

    public static double round(double value, int places) {

        if (places < 0) throw new IllegalArgumentException("param places invalid: " + places);

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName, ingredientAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            ingredientName = itemView.findViewById(R.id.ingredient_name);
            ingredientAmount = itemView.findViewById(R.id.ingredient_amount);

        }


    }
}
