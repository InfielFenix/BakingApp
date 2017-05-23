package com.projects.alexanderauer.bakingapp.services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.projects.alexanderauer.bakingapp.R;
import com.projects.alexanderauer.bakingapp.entity.Ingredient;
import com.projects.alexanderauer.bakingapp.entity.Recipe;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Alex on 20.05.2017.
 */
public class RecipeStackViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackViewsRemoteFactory(this.getApplicationContext(), intent);
    }
}

class StackViewsRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Recipe> mRecipes;
    private Context mContext;

    public StackViewsRemoteFactory(Context context, Intent intent) {
        mContext = context;
    }

    public void onCreate() {
    }

    public void onDestroy() {
        mRecipes.clear();
    }

    public int getCount() {
        if (mRecipes == null)
            return 0;

        return mRecipes.size();
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_item);

        Recipe recipe = mRecipes.get(position);

        rv.setTextViewText(R.id.widget_item_recipe_name, recipe.getName());

        String ingredients = "";
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredients += " - " + ingredient.getIngredient() + "\n";
        }

        rv.setTextViewText(R.id.widget_item_ingredients,ingredients);

        Bundle extras = new Bundle();
        extras.putParcelable(mContext.getString(R.string.extra_recipe),recipe);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.recipe_widget_item,fillIntent);

        return rv;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        if(mRecipes == null) {
            Uri builtUri = Uri.parse(mContext.getString(R.string.link_2_recipes));

            HttpURLConnection urlConnection = null;
            try {
                // get URL
                URL url = new URL(builtUri.toString());
                // open connection
                urlConnection = (HttpURLConnection) url.openConnection();

                // create an input stream reader
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());

                Recipe[] recipeArray = new Gson().fromJson(reader, Recipe[].class);
                mRecipes = new ArrayList<>(Arrays.asList(recipeArray));

            } catch (MalformedURLException e) {
                Log.e("MalformedURLException", e.getMessage());
            } catch (IOException e) {
                Log.e("IOException", e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }
}