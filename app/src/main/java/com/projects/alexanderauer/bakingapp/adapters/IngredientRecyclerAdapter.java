package com.projects.alexanderauer.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.alexanderauer.bakingapp.R;
import com.projects.alexanderauer.bakingapp.entity.Ingredient;

import java.util.List;

/**
 * Created by Alex on 17.05.2017.
 */

public class IngredientRecyclerAdapter extends RecyclerView.Adapter<IngredientRecyclerAdapter.ViewHolder> {

    private List<Ingredient> mIngredients;

    public IngredientRecyclerAdapter(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Ingredient mBoundIngredient;

        public final View mView;
        public final TextView mIngredient;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mIngredient = (TextView) itemView.findViewById(R.id.ingredient);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBoundIngredient = mIngredients.get(position);
        holder.mIngredient.setText(holder.mBoundIngredient.getQuantity() + " " +
                holder.mBoundIngredient.getMeasure() + " " +
                holder.mBoundIngredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null)
            return 0;
        else
            return mIngredients.size();
    }
}