package com.mytaxi.android_demo;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.mytaxi.android_demo.activities.MainActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> myactivityTestRule = new ActivityTestRule(MainActivity.class);

    @BeforeClass
    public static void grantPermision()
    {
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("pm grant com.mytaxi.android_demo android.permission.ACCESS_FINE_LOCATION");
    }

    @Test
    public void loginCase() throws Exception {
        onView(withId(R.id.edt_username)).check(matches(isDisplayed()));
        onView(withId(R.id.edt_username)).perform(typeText("whiteelephant261"), closeSoftKeyboard());
        onView(withId(R.id.edt_password)).perform(typeText("video"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.searchContainer)).check(matches(isDisplayed()));
    }

    @Test
    public void searchDriver() throws Exception {
        loginCase();
        onView(withId(R.id.textSearch)).perform(typeText("sa"));
        onView(withText("Sarah Friedrich")).inRoot(RootMatchers.withDecorView(not(is(myactivityTestRule.getActivity().getWindow().getDecorView())))).perform(click());
        onView(withId(R.id.fab)).perform(click());
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("pm clear com.android.dialer");
        pressBack();
    }

    @After
    public void logoutCase() throws Exception {

        onView(withContentDescription("Open navigation drawer")).perform(click());
        onView(withText("Logout")).perform(click());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

}