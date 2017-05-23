package com.projects.alexanderauer.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.alexanderauer.bakingapp.adapters.IngredientRecyclerAdapter;
import com.projects.alexanderauer.bakingapp.adapters.StepRecyclerAdapter;
import com.projects.alexanderauer.bakingapp.entity.Recipe;
import com.projects.alexanderauer.bakingapp.entity.Step;
import com.squareup.picasso.Picasso;

/**
 * Created by Alex on 18.05.2017.
 */

public class RecipeDetailFragment extends Fragment {

    OnRecipeStepClickListener mCallback;

    public interface OnRecipeStepClickListener {
        void onRecipeStepClicked(Step step);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnRecipeStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeStepClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the fragment_main layout
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        if (getActivity().getIntent().hasExtra(getString(R.string.extra_recipe))) {
            // get movie object from extras
            final Recipe recipe = getActivity().getIntent().getParcelableExtra(getString(R.string.extra_recipe));

            getActivity().setTitle(recipe.getName());

            TextView tvRecipeName = (TextView) rootView.findViewById(R.id.recipe_detail_name);
            tvRecipeName.setText(recipe.getName());

            ImageView ivRecipeImage = (ImageView) rootView.findViewById(R.id.recipe_detail_image);
            if(!recipe.getImagePath().equals("")) {
                Picasso.with(getContext())
                        .load(recipe.getImagePath() + getContext().getString(R.string.recipe_image_size))
                        .into(ivRecipeImage);
            }

            RecyclerView rvIngredients = (RecyclerView) rootView.findViewById(R.id.ingredients);
            if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
                rvIngredients.setLayoutManager(new LinearLayoutManager(rvIngredients.getContext()));
                rvIngredients.setAdapter(new IngredientRecyclerAdapter(recipe.getIngredients()));
            }

            final RecyclerView rvSteps = (RecyclerView) rootView.findViewById(R.id.steps);
            if(recipe.getSteps() != null && recipe.getSteps().size() > 0){
                rvSteps.setLayoutManager(new LinearLayoutManager(rvSteps.getContext()));
                rvSteps.setAdapter(new StepRecyclerAdapter(recipe.getSteps(),mCallback));
            }
        }

        return rootView;
    }
}
