package com.example.cps731project;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.cps731project.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<SignUp> mActivityTestRule = new ActivityTestRule<>(SignUp.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatTextView = onView(
allOf(withId(R.id.haveAccount), withText("Have an Account? Login here."),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()));
        appCompatTextView.perform(click());
        
        ViewInteraction appCompatEditText = onView(
allOf(withId(R.id.loginEmail),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
2),
isDisplayed()));
        appCompatEditText.perform(replaceText("bob@gmail.com"), closeSoftKeyboard());
        
        ViewInteraction appCompatEditText2 = onView(
allOf(withId(R.id.loginPassword),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
3),
isDisplayed()));
        appCompatEditText2.perform(replaceText("123456"), closeSoftKeyboard());
        
        ViewInteraction appCompatEditText3 = onView(
allOf(withId(R.id.loginPassword), withText("123456"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
3),
isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());
        
        ViewInteraction appCompatButton = onView(
allOf(withId(R.id.btnLogin), withText("LOGIN"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
4),
isDisplayed()));
        appCompatButton.perform(click());
        
        ViewInteraction textView = onView(
allOf(withId(android.R.id.text1), withText("Snack"),
withParent(allOf(withId(R.id.mealType),
withParent(withId(R.id.mainActivity)))),
isDisplayed()));
        textView.check(matches(withText("Snack")));
        
        ViewInteraction appCompatSpinner = onView(
allOf(withId(R.id.mealType),
childAtPosition(
allOf(withId(R.id.mainActivity),
childAtPosition(
withId(android.R.id.content),
0)),
1),
isDisplayed()));
        appCompatSpinner.perform(click());
        
        ViewInteraction appCompatSpinner2 = onView(
allOf(withId(R.id.mealType),
childAtPosition(
allOf(withId(R.id.mainActivity),
childAtPosition(
withId(android.R.id.content),
0)),
1),
isDisplayed()));
        appCompatSpinner2.perform(click());
        
        DataInteraction appCompatTextView2 = onData(anything())
.inAdapterView(childAtPosition(
withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
0))
.atPosition(0);
        appCompatTextView2.perform(click());
        }
    
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
