package com.projects.alexanderauer.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Alex on 22.05.2017.
 *
 *  This test navigates threw the application and checks the data
 *
 */

@RunWith(AndroidJUnit4.class)
public class BakingAppNavigationTest {

    static final String RECIPE_NAME = "Nutella Pie",
        RECIPE_STEP_DESCRIPTION= "Recipe Introduction";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeAndCheckIntent() {

        // click on the first recipe in the Recycler View
        onView(withId(R.id.main_fragment)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the RecipeDetailActivity opens with the correct recipe displayed
        onView(withId(R.id.recipe_detail_name)).check(matches(withText(RECIPE_NAME)));

        // go further to the first step detail
        onView(withId(R.id.steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the RecipeDetailActivity opens with the correct recipe step displayed
        onView(withId(R.id.step_detail_description)).check(matches(withText(RECIPE_STEP_DESCRIPTION)));

        // go back
        onView(isRoot()).perform(pressBack());

        onView(withId(R.id.recipe_detail_name)).check(matches(withText(RECIPE_NAME)));
    }
}