package com.udacity.sanketbhat.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.sanketbhat.bakingapp.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class IdlingResourcesMainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void beforeTesting() {
        idlingResource = testRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void mainActivity_IdlingResourceTest() {
        onView(withId(R.id.recipeRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));
    }

    @Test
    public void clickRecipe_LaunchesStepActivity() {
        onView(withId(R.id.recipeRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.step_list)).check(matches(isDisplayed()));
    }

    @Test
    public void pressBackInStepActivity_ExitsStepActivity() {
        onView(withId(R.id.recipeRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.step_list)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.recipeRecyclerView))
                .check(matches(isDisplayed()));
    }

    @Test
    public void onClick_Brownies_LaunchesStepActivity() {
        onView(withText("Brownies"))
                .perform(click());
        onView(withId(R.id.step_list))
                .check(matches(isDisplayed()));
    }

    @Test
    public void onClickRecipe_CheckStepActivityHasExtras() {
        onView(withId(R.id.recipeRecyclerView))
                .perform(actionOnItemAtPosition(1, click()));
        onView(withId(R.id.step_list))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ingredientsRecyclerView))
                .check(matches(isDisplayed()));
    }

    @After
    public void afterTesting() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
