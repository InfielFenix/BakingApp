package com.projects.alexanderauer.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.projects.alexanderauer.bakingapp.entity.Recipe;
import com.projects.alexanderauer.bakingapp.entity.Step;

/**
 * Created by Alex on 18.05.2017.
 */

public class StepDetailFragment extends Fragment {

    OnStepButtonClickListener mCallback;

    public interface OnStepButtonClickListener {
        void onStepButtonClickedPrev(Step step);
        void onStepButtonClickedNext(Step step);
    }

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Step mStep;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallback = (OnStepButtonClickListener) StepDetailFragment.this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the fragment_main layout
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        Step step = null;
        if (getActivity().getIntent().hasExtra(getString(R.string.extra_step)))
            // get movie object from extras
            step = getActivity().getIntent().getParcelableExtra(getString(R.string.extra_step));
        else if (mStep != null)
            step = mStep;

        Recipe recipe = null;
        if(getActivity().getIntent().hasExtra(getString(R.string.extra_recipe)))
            recipe = getActivity().getIntent().getParcelableExtra(getString(R.string.extra_recipe));

        if (step != null) {
            getActivity().setTitle(step.getShortDescription());

            // Initialize the player view.
            mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

            TextView tvStepDescription = (TextView) rootView.findViewById(R.id.step_detail_description);
            tvStepDescription.setText(step.getDescription());

            Button btnPrevStep = (Button) rootView.findViewById(R.id.button_prev_step);

            final Step finalStep = step;
            final Recipe finalRecipe = recipe;
            if (btnPrevStep != null) {
                btnPrevStep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCallback.onStepButtonClickedPrev(finalStep);
                    }
                });
            }

            Button btnNextStep = (Button) rootView.findViewById(R.id.button_next_step);
            if (btnNextStep != null) {
                btnNextStep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCallback.onStepButtonClickedNext(finalStep);
                    }
                });
            }

            initializePlayer(Uri.parse(step.getVideoURL()));
        }

        return rootView;
    }

    public void setData(Step step) {
        this.mStep = step;
    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "StepVideo");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
}
