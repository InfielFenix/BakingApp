package com.projects.alexanderauer.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.projects.alexanderauer.bakingapp.adapters.RecipeAdapter;
import com.projects.alexanderauer.bakingapp.entity.Recipe;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Alex on 16.05.2017.
 */

public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>> {

    private RecyclerView rvRecipes;

    private static final int RECIPE_LOADER_ID = 0;

    private ArrayList<Recipe> recipeBuffer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // init loader which Sloads the movies from TheMovieDb
        getLoaderManager().initLoader(RECIPE_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the fragment_main layout
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // get and lay down the grid view
        rvRecipes = (RecyclerView) rootView.findViewById(R.id.recipes);

        return rootView;
    }

    private class RecipeCollection {
        @SerializedName("results")
        ArrayList<Recipe> recipes;
    }


    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(getActivity()) {

            @Override
            protected void onStartLoading() {
                if (recipeBuffer != null) {
                    // Delivers any previously loaded movies data immediately
                    deliverResult(recipeBuffer);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Recipe> loadInBackground() {
                // build the correct URI
                Uri builtUri = Uri.parse(getString(R.string.link_2_recipes));

                HttpURLConnection urlConnection = null;
                try {
                    // get URL
                    URL url = new URL(builtUri.toString());
                    // open connection
                    urlConnection = (HttpURLConnection) url.openConnection();

                    // create an input stream reader
                    InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());

                    Recipe[] recipeArray = new Gson().fromJson(reader, Recipe[].class);
                    recipeBuffer = new ArrayList<Recipe>(Arrays.asList(recipeArray));

                    return recipeBuffer;
                } catch (MalformedURLException e) {
                    Log.e("MalformedURLException", e.getMessage());
                } catch (IOException e) {
                    Log.e("IOException", e.getMessage());
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

                return null;
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(ArrayList<Recipe> data) {
                recipeBuffer = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        if(!getResources().getBoolean(R.bool.isTablet))
            rvRecipes.setLayoutManager(new LinearLayoutManager(rvRecipes.getContext()));
        else
            rvRecipes.setLayoutManager(new GridLayoutManager(rvRecipes.getContext(),3));

        rvRecipes.setAdapter(new RecipeAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

    }
}
