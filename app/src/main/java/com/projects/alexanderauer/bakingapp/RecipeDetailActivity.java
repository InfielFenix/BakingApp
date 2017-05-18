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

    public final static int REQUEST_CODE_STEP_DETAIL = 0,
            RESULT_CODE_BTN_PREV = 0,
            RESULT_CODE_BTN_NEXT = 1;

    Recipe mRecipe = null;

    public static RecipeDetailActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mActivity = this;

        // activate the back arrow in the upper left corner of the app
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra(getString(R.string.extra_recipe)))
            mRecipe = getIntent().getParcelableExtra(getString(R.string.extra_recipe));
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
    public void onRecipeStepClicked(Step step) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setData(step);

            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_detail_fragment, stepDetailFragment).commit();
        } else {
            // create intent, put the step data object into its extras and start the activity
            Intent stepDetailIntent = new Intent(this, StepDetailActivity.class);

            stepDetailIntent.putExtra(getString(R.string.extra_step), step);

            startActivityForResult(stepDetailIntent, REQUEST_CODE_STEP_DETAIL);
        }
    }

    @Override
    public void onStepButtonClickedPrev(Step step) {
        if (step != null && mRecipe != null) {
           // onRecipeStepClicked(mRecipe.getSteps().get(step.getId() - 1));
            Step newStep = null;

            if (step.getId() == 0)
                newStep = mRecipe.getSteps().get(mRecipe.getSteps().size()-1);
            else
                newStep = mRecipe.getSteps().get(step.getId() - 1);

            onRecipeStepClicked(newStep);
        }
    }

    @Override
    public void onStepButtonClickedNext(Step step) {
        if (step != null && mRecipe != null) {
            //onRecipeStepClicked(mRecipe.getSteps().get(step.getId() + 1));
            Step newStep = null;

            if (step.getId() == mRecipe.getSteps().size()-1)
                newStep = mRecipe.getSteps().get(0);
            else
                newStep = mRecipe.getSteps().get(step.getId() + 1);

            onRecipeStepClicked(newStep);
        }
    }

    private void replaceStepDetail(Step step){
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setData(step);

        getSupportFragmentManager().beginTransaction().replace(R.id.recipe_detail_fragment, stepDetailFragment).commit();
    }
}
