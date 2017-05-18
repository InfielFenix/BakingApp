package com.projects.alexanderauer.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.alexanderauer.bakingapp.R;
import com.projects.alexanderauer.bakingapp.RecipeDetailFragment;
import com.projects.alexanderauer.bakingapp.entity.Step;

import java.util.List;

/**
 * Created by Alex on 17.05.2017.
 */

public class StepRecyclerAdapter extends RecyclerView.Adapter<StepRecyclerAdapter.ViewHolder> {

    private List<Step> mSteps;
    private RecipeDetailFragment.OnRecipeStepClickListener mCallback;

    public StepRecyclerAdapter(List<Step> steps, RecipeDetailFragment.OnRecipeStepClickListener mCallback) {
        this.mSteps = steps;
        this.mCallback = mCallback;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        private Context mContext;

        public Step mBoundStep;

        private RecipeDetailFragment.OnRecipeStepClickListener mListener;

        public final View mView;
        public final TextView mShortDescription;

        public ViewHolder(View itemView,RecipeDetailFragment.OnRecipeStepClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();

            mView = itemView;
            mShortDescription = (TextView) itemView.findViewById(R.id.step_short_description);
        }

        @Override
        public void onClick(View view) {
            mListener.onRecipeStepClicked(mBoundStep);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);

        return new ViewHolder(view,mCallback);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBoundStep = mSteps.get(position);
        holder.mShortDescription.setText((position+1) + ". " + holder.mBoundStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mSteps == null)
            return 0;
        else
            return mSteps.size();
    }
}