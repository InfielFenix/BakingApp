package com.projects.alexanderauer.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.projects.alexanderauer.bakingapp.entity.Recipe;
import com.projects.alexanderauer.bakingapp.entity.Step;


/**
 * Created by Alex on 17.05.2017.
 */

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnRecipeStepClickListener, StepDetailFragment.OnStepButtonClickListener {

    Recipe mRecipe = null;

    int mCurrentStep = 0;

    public static RecipeDetailActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.current_step_extra)))
            mCurrentStep = savedInstanceState.getInt(getString(R.string.current_step_extra));

        mActivity = this;

        // activate the back arrow in the upper left corner of the app
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra(getString(R.string.extra_recipe))) {
            mRecipe = getIntent().getParcelableExtra(getString(R.string.extra_recipe));

            // automatically select current item when in two pane mode
            if(getResources().getBoolean(R.bool.isTablet))
                onRecipeStepClicked(mRecipe.getSteps().get(mCurrentStep));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // trigger the onBackPressed event when back arrow gets pressed
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // store data
        outState.putInt(getString(R.string.current_step_extra), mCurrentStep);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRecipeStepClicked(Step step) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            mCurrentStep = step.getId();
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setData(step);

            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment, stepDetailFragment).commit();
        } else {
            // create intent, put the step data object into its extras and start the activity
            Intent stepDetailIntent = new Intent(this, StepDetailActivity.class);

            stepDetailIntent.putExtra(getString(R.string.extra_step), step);

            startActivity(stepDetailIntent);
        }
    }

    @Override
    public void onStepButtonClickedPrev(Step step) {
        if (step != null && mRecipe != null) {
            if (step.getId() == 0)
                mCurrentStep = mRecipe.getSteps().size() - 1;
            else
                mCurrentStep = step.getId() - 1;

            onRecipeStepClicked(mRecipe.getSteps().get(mCurrentStep));
        }
    }

    @Override
    public void onStepButtonClickedNext(Step step) {
        if (step != null && mRecipe != null) {
            if (step.getId() == mRecipe.getSteps().size()-1)
                mCurrentStep = 0;
            else
                mCurrentStep = step.getId() + 1;

            onRecipeStepClicked(mRecipe.getSteps().get(mCurrentStep));
        }
    }
}
