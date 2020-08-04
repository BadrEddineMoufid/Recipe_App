package com.badr.recipe_app.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.badr.recipe_app.Model.Step;
import com.badr.recipe_app.R;

import java.util.List;

public class instructionsAdapter extends RecyclerView.Adapter<instructionsAdapter.ViewHolder> {
    private Context context;
    private List<Step> stepsList;
    private static final String TAG = "instructionsAdapter";

    public instructionsAdapter(Context context, List<Step> stepsList) {
        this.context = context;
        this.stepsList = stepsList;

        Log.d(TAG, "steps list: " + stepsList.get(0).getNumber() + stepsList.get(0).getStep());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating layout this is so fucking repetitive
        //todo: test an adapter that accept a list<T> and determine what layout to inflate and what view to bind and view to set in ViewHolder class based on the type of the list

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //getting current step
        Step currentStep = stepsList.get(position);

        Log.d(TAG, "steps list: " + currentStep.getNumber() + currentStep.getStep());

        holder.stepNumber.setText(String.valueOf(currentStep.getNumber()));
        holder.stepContent.setText(currentStep.getStep());
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView stepNumber, stepContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stepNumber = itemView.findViewById(R.id.step_number);
            stepContent = itemView.findViewById(R.id.step_content);
        }
    }
}
