package com.tahutelorcommunity.bakingapp.ui;


import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.tahutelorcommunity.bakingapp.R;
import com.tahutelorcommunity.bakingapp.RecyclerViewMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeNavigationTest {

    private String recipe_name = "Nutella Pie";
    private String step_short_description = "Recipe Introduction";
    private String step_description = "Recipe Introduction";
    private String step_number = "0";
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mActivityTestRule.getActivity().getSimpleIdlingResource();
        registerIdlingResources(mIdlingResource);
    }

    @Test
    public void navigationTest() {
        onView(withId(R.id.recipe_recycler_view)).perform(scrollToPosition(0));
        onView(withRecyclerView(R.id.recipe_recycler_view)
                .atPositionOnView(0, R.id.recipe_name))
                .check(matches(withText(recipe_name)));

        onView(withRecyclerView(R.id.recipe_recycler_view)
                .atPosition(0)).perform(click());

        onView(withId(R.id.step_recycler_view)).perform(scrollToPosition(0));
        onView(withRecyclerView(R.id.step_recycler_view)
                .atPositionOnView(0, R.id.step_short_description))
                .check(matches(withText(step_short_description)));

        onView(withRecyclerView(R.id.step_recycler_view)
                .atPosition(0)).perform(click());

        onView(withId(R.id.step_detail_description)).check(matches(withText(step_description)));
    }

    @After
    public void unregisterIdlingResource(){
        if(mIdlingResource != null){
            unregisterIdlingResources(mIdlingResource);
        }
    }
}
