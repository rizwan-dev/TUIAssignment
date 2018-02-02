package com.systemplus.tuiassignment.ui.home;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.systemplus.tuiassignment.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.systemplus.tuiassignment.util.EspressoTestUtil.matchToolbarTitle;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Performs UI test for HomeActivity
 * @author Rizwanul Haque
 */

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {


    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class);


    @Test
    public void verifyUIComponents() {

        //Verify toolbar
        CharSequence title = InstrumentationRegistry.getTargetContext()
                .getString(R.string.home);
        matchToolbarTitle(title);

        //Verify TextInput button
        onView(withId(R.id.btnTextInput)).check(matches(isDisplayed()));

        //Verify Random Joke button
        onView(withId(R.id.btnRandomJoke)).check(matches(isDisplayed()));

        //Verify NeverEndingList button
        onView(withId(R.id.btnNeverEndingList)).check(matches(isDisplayed()));

        //Verify ExcludeExplicit checkbox
        onView(withId(R.id.cbExcludeExplicit)).check(matches(isDisplayed()));

    }

    @Test
    public void clickRandomJokesButton_showsDialogSuccess() {
        // TODO : Need to mock reponse for success case
        onView(withId(R.id.btnRandomJoke)).perform(click());
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));

    }

    @Test
    public void clickRandomJokesButton_showsDialogError() {
        // TODO : Need to verify error case with mock response

    }

    @Test
    public void clickRandomJokesButton_showsDialogNetworkError() {
        // TODO : Need to verify network error with mocking

    }


    @Test
    public void clickTextInputButton_opensTextInputScreen() {
        onView(withId(R.id.btnTextInput)).perform(click());
        onView(withId(R.id.edtUserName)).check(matches(allOf(isDescendantOfA(withId(R.id.linLayTextInput)), isDisplayed())));
    }

    @Test
    public void clickNeverEndingListButton_opensNeverEndingListScreen() {
        onView(withId(R.id.btnNeverEndingList)).perform(click());
        onView(withId(R.id.jokeList)).check(matches(allOf(isDescendantOfA(withId(R.id.flNeverEndingList)), isDisplayed())));
    }

}