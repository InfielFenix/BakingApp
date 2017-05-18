package com.projects.alexanderauer.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.alexanderauer.bakingapp.R;
import com.projects.alexanderauer.bakingapp.RecipeDetailActivity;
import com.projects.alexanderauer.bakingapp.entity.Recipe;

import java.util.List;

/**
 * Created by Alex on 17.05.2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> mRecipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.mRecipes = recipes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        private Context mContext;

        public Recipe mBoundRecipe;

        public final View mView;
        public final TextView mRecipeName, mServings, mSteps;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();

            mView = itemView;
            mRecipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            mServings = (TextView) itemView.findViewById(R.id.servings);
            mSteps = (TextView) itemView.findViewById(R.id.steps);
        }

        @Override
        public void onClick(View view) {
            // create intent, put the recipe data object into its extras and start the activity
            Intent recipeDetailIntent = new Intent(mContext, RecipeDetailActivity.class);

            recipeDetailIntent.putExtra(mView.getContext().getString(R.string.extra_recipe), mBoundRecipe);

            mContext.startActivity(recipeDetailIntent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBoundRecipe = mRecipes.get(position);
        holder.mRecipeName.setText(holder.mBoundRecipe.getName());
        holder.mServings.setText(Integer.toString(holder.mBoundRecipe.getServings()) + " " + holder.mView.getContext().getString(R.string.servings));
        holder.mSteps.setText(Integer.toString(holder.mBoundRecipe.getSteps().size()) + " " + holder.mView.getContext().getString(R.string.steps));
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null)
            return 0;
        else
            return mRecipes.size();
    }
}