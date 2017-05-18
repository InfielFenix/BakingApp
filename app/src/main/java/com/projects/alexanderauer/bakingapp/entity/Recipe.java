package com.projects.alexanderauer.bakingapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Alex on 16.05.2017.
 */

public class Recipe implements Parcelable{

    @SerializedName("id")
    int id;

    @SerializedName("servings")
    int servings;

    @SerializedName("name")
    String name;

    @SerializedName("image")
    String imagePath;

    @SerializedName("ingredients")
    ArrayList<Ingredient> ingredients;

    @SerializedName("steps")
    ArrayList<Step> steps;

    public Recipe() {
    }

    public Recipe(int id, int servings, String name, String imagePath) {
        this.id = id;
        this.servings = servings;
        this.name = name;
        this.imagePath = imagePath;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        servings = in.readInt();
        name = in.readString();
        imagePath = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(servings);
        dest.writeString(name);
        dest.writeString(imagePath);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        if(steps == null)
            steps = new ArrayList<Step>();

        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }
}
