package partyplaner.partyplaner.LogIn;

/**
 * Tests auf die Existenz in
 * Kontaktformular
 *
 */
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import partyplaner.partyplaner.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
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
public class KontaktformularTest {

    @Rule
    public ActivityTestRule<LogInActivity> mActivityTestRule = new ActivityTestRule<>(LogInActivity.class);

    @Test
    public void kontaktformularTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                allOf(withId(R.id.fragment4),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());

        appCompatEditText = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                allOf(withId(R.id.fragment4),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("testvom@test.de"), closeSoftKeyboard());

        appCompatEditText = onView(
                allOf(withId(R.id.username), withText("testvom@test.de"),
                        childAtPosition(
                                allOf(withId(R.id.fragment4),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(pressImeActionButton());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                allOf(withId(R.id.fragment4),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("test"), closeSoftKeyboard());

        appCompatEditText2 = onView(
                allOf(withId(R.id.password), withText("test"),
                        childAtPosition(
                                allOf(withId(R.id.fragment4),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Anmelden"),
                        childAtPosition(
                                allOf(withId(R.id.fragment4),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());
/*
        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        8),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
*/
        ViewInteraction textView = onView(
                allOf(withId(R.id.betreffText), withText("Betreff :"),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.betreffInputText),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.VornameText), withText("Name :"),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.VornameInput),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.NachnameText), withText("Nachname :"),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.NachnameInput),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        editText3.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.TelefonText), withText("Telefon :"),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                6),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.TelefonInput),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                7),
                        isDisplayed()));
        editText4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.EmailText), withText("E-Mail :"),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                8),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.EmailInput),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                9),
                        isDisplayed()));
        editText5.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.Email2Text), withText("E-Mail wiederholen :"),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                10),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.Email2Input),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                11),
                        isDisplayed()));
        editText6.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.AnfrageText), withText("Ihre Anfrage"),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                12),
                        isDisplayed()));
        textView7.check(matches(isDisplayed()));

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.AnfrageInput),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                13),
                        isDisplayed()));
        editText7.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.SendButton),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.content),
                                                0)),
                                14),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

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
