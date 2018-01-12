package partyplaner.partyplaner.LogIn;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import partyplaner.partyplaner.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegistryTest {

    @Rule
    public ActivityTestRule<LogInActivity> mActivityTestRule = new ActivityTestRule<>(LogInActivity.class);

    @Before
    public void Registry() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.signInRegister), withText("Registrieren"),
                        childAtPosition(
                                allOf(withId(R.id.fragment4),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());
    }
    @Test
    public void registryEMailTextAreaExistsTest() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.regEmail),
                        childAtPosition(
                                allOf(withId(R.id.fragment),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                2),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));
    }

    @Test
    public void registryPassword2TextAreaExistsTest() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.regPassWdg),
                        childAtPosition(
                                allOf(withId(R.id.fragment),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                4),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));
    }

    @Test
    public void registryPasswordTextAreaExistsTest() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.regPass),
                        childAtPosition(
                                allOf(withId(R.id.fragment),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                3),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));
    }

    @Test
    public void registryRegistryButtonExistsTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.regSave),
                        childAtPosition(
                                allOf(withId(R.id.fragment),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                5),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void registryRegistryLabelExistsWithTextTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.textView),
                        childAtPosition(
                                allOf(withId(R.id.fragment),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    }

    @Test
    public void registryUserNameTextAreaWorksTest() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.regName),
                        childAtPosition(
                                allOf(withId(R.id.fragment),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                1),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));
    }

    @Test
    public void registryPaplaLogoExistsTest() {
        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView2), withContentDescription("Papla Logo"),
                        childAtPosition(
                                allOf(withId(R.id.logo),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));
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
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
