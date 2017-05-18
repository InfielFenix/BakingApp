package com.projects.alexanderauer.bakingapp;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.projects.alexanderauer.bakingapp.entity.Step;

/**
 * Created by Alex on 17.05.2017.
 */

public class StepDetailActivity extends AppCompatActivity implements StepDetailFragment.OnStepButtonClickListener {

    StepDetailFragment.OnStepButtonClickListener mCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        // activate the back arrow in the upper left corner of the app
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void onStepButtonClickedPrev(Step step) {
        RecipeDetailActivity.mActivity.onStepButtonClickedPrev(step);
    }

    @Override
    public void onStepButtonClickedNext(Step step) {
        RecipeDetailActivity.mActivity.onStepButtonClickedNext(step);
    }
}
