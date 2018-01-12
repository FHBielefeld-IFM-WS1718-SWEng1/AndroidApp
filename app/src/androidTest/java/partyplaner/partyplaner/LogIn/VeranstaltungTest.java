package partyplaner.partyplaner.LogIn;

/**
 * Tests auf die Existenz in
 * Veranstaltungen
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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import partyplaner.partyplaner.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
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
public class VeranstaltungTest {

    @Rule
    public ActivityTestRule<LogInActivity> mActivityTestRule = new ActivityTestRule<>(LogInActivity.class);

    @Test
    public void veranstaltungTest() {
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

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                allOf(withId(R.id.fragment4),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("testvom@test.de"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                allOf(withId(R.id.fragment4),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.password), withText("test"),
                        childAtPosition(
                                allOf(withId(R.id.fragment4),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

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

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        3),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.own_events_back),
                        childAtPosition(
                                allOf(withId(R.id.ownEvent_list),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                0)));
        linearLayout.perform(scrollTo(), click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView7),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withText("Was?:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withText("Wer?:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withText("Wo?:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                2),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withText("Wann?:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                3),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.event_description),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                1),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.event_what),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                0),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.event_who),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                1),
                        isDisplayed()));
        textView7.check(matches(isDisplayed()));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.event_where),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                2),
                        isDisplayed()));
        textView8.check(matches(isDisplayed()));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.event_when),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                3),
                        isDisplayed()));
        textView9.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.button_more),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                2),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

//Gallerie öffnen
        ViewInteraction relativeLayout3 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                0)),
                                0)));
        relativeLayout3.perform(scrollTo(), click());

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.expandableTitle), withText("Gallerie"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                1),
                        isDisplayed()));
        textView10.check(matches(isDisplayed()));
/*
        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.arrow), withContentDescription("arrow"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));
*/
        ViewInteraction relativeLayout10 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                0)),
                                0),
                        isDisplayed()));
        relativeLayout10.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.addPictures),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        relativeLayout3 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                0)),
                                0)));
        relativeLayout3.perform(scrollTo(), click());


        //Aufgaben
        ViewInteraction relativeLayout4 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                1)),
                                0)));
        relativeLayout4.perform(scrollTo(), click());
/*
        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.arrow), withContentDescription("arrow"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                0),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));
*/
        ViewInteraction textView11 = onView(
                allOf(withId(R.id.expandableTitle), withText("Aufgaben"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                1),
                        isDisplayed()));
        textView11.check(matches(isDisplayed()));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.expandableTitle), withText("Aufgaben"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                1),
                        isDisplayed()));
        textView12.check(matches(isDisplayed()));

        ViewInteraction relativeLayout11 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                1)),
                                0),
                        isDisplayed()));
        relativeLayout11.check(matches(isDisplayed()));
/*
        ViewInteraction textView13 = onView(
                allOf(withText("Aufgabe"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView13.check(matches(isDisplayed()));
*/
        ViewInteraction textView14 = onView(
                allOf(withText("Name"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView14.check(matches(isDisplayed()));

        ViewInteraction textView15 = onView(
                allOf(withText("Status"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        textView15.check(matches(isDisplayed()));

        relativeLayout4 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                1)),
                                0)));
        relativeLayout4.perform(scrollTo(), click());


        //TOD.O
        ViewInteraction relativeLayout5 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                2)),
                                0)));
        relativeLayout5.perform(scrollTo(), click());
/*
        ViewInteraction imageView4 = onView(
                allOf(withId(R.id.arrow), withContentDescription("arrow"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                0),
                        isDisplayed()));
        imageView4.check(matches(isDisplayed()));
*/
        ViewInteraction textView16 = onView(
                allOf(withId(R.id.expandableTitle), withText("TODO"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                1),
                        isDisplayed()));
        textView16.check(matches(isDisplayed()));

        ViewInteraction relativeLayout12 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                2)),
                                0),
                        isDisplayed()));
        relativeLayout12.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.button2),
                        childAtPosition(
                                allOf(withId(R.id.body_todo),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction textView17 = onView(
                allOf(withText("Aufgabe"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView17.check(matches(isDisplayed()));

        ViewInteraction textView18 = onView(
                allOf(withText("Kosten"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView18.check(matches(isDisplayed()));

        relativeLayout5 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                2)),
                                0)));
        relativeLayout5.perform(scrollTo(), click());


        //Gäste
        ViewInteraction relativeLayout6 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                3)),
                                0)));
        relativeLayout6.perform(scrollTo(), click());
/*
        ViewInteraction imageView5 = onView(
                allOf(withId(R.id.arrow), withContentDescription("arrow"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                0),
                        isDisplayed()));
        imageView5.check(matches(isDisplayed()));
*/
        ViewInteraction textView19 = onView(
                allOf(withId(R.id.expandableTitle), withText("Gäste"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                1),
                        isDisplayed()));
        textView19.check(matches(isDisplayed()));

        ViewInteraction relativeLayout13 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                3)),
                                0),
                        isDisplayed()));
        relativeLayout13.check(matches(isDisplayed()));
/*
        ViewInteraction textView20 = onView(
                allOf(withId(android.R.id.title), withText("ZUSAGEN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.tabs),
                                        0),
                                0),
                        isDisplayed()));
        textView20.check(matches(isDisplayed()));

        ViewInteraction textView21 = onView(
                allOf(withId(android.R.id.title),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.tabs),
                                        1),
                                0),
                        isDisplayed()));
        textView21.check(matches(isDisplayed()));

        ViewInteraction textView22 = onView(
                allOf(withId(android.R.id.title), withText("AUSSTEHEND"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.tabs),
                                        2),
                                0),
                        isDisplayed()));
        textView22.check(matches(isDisplayed()));
*/
        relativeLayout6 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                3)),
                                0)));
        relativeLayout6.perform(scrollTo(), click());


        //Abstimmungen
        ViewInteraction relativeLayout7 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                4)),
                                0)));
        relativeLayout7.perform(scrollTo(), click());
/*
        ViewInteraction imageView6 = onView(
                allOf(withId(R.id.arrow), withContentDescription("arrow"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                0),
                        isDisplayed()));
        imageView6.check(matches(isDisplayed()));
*/
        ViewInteraction textView23 = onView(
                allOf(withId(R.id.expandableTitle), withText("Abstimmungen"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                1),
                        isDisplayed()));
        textView23.check(matches(isDisplayed()));

        ViewInteraction relativeLayout14 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                4)),
                                0),
                        isDisplayed()));
        relativeLayout14.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.create_poll),
                        childAtPosition(
                                allOf(withId(R.id.body_poll),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        relativeLayout7 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                4)),
                                0)));
        relativeLayout7.perform(scrollTo(), click());


        //Bewertungen
        ViewInteraction relativeLayout8 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                5)),
                                0)));
        relativeLayout8.perform(scrollTo(), click());
/*
        ViewInteraction imageView7 = onView(
                allOf(withId(R.id.arrow), withContentDescription("arrow"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                0),
                        isDisplayed()));
        imageView7.check(matches(isDisplayed()));
*/
        ViewInteraction textView24 = onView(
                allOf(withId(R.id.expandableTitle), withText("Bewertungen"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                1),
                        isDisplayed()));
        textView24.check(matches(isDisplayed()));

        ViewInteraction relativeLayout15 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                5)),
                                0),
                        isDisplayed()));
        relativeLayout15.check(matches(isDisplayed()));

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.posRating),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout2.check(matches(isDisplayed()));
/*
        ViewInteraction linearLayout3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                0),
                        1),
                        isDisplayed()));
        linearLayout3.check(matches(isDisplayed()));
*/
        ViewInteraction imageView8 = onView(
                allOf(withId(R.id.profile_picture),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        imageView8.check(matches(isDisplayed()));

        ViewInteraction imageView9 = onView(
                allOf(withId(R.id.imageView6),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        imageView9.check(matches(isDisplayed()));

        ViewInteraction textView25 = onView(
                allOf(withId(R.id.numRates),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        textView25.check(matches(isDisplayed()));

        relativeLayout8 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                5)),
                                0)));
        relativeLayout8.perform(scrollTo(), click());


        //Kommentare
        ViewInteraction relativeLayout9 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                6)),
                                0)));
        relativeLayout9.perform(scrollTo(), click());
/*
        ViewInteraction imageView10 = onView(
                allOf(withId(R.id.arrow), withContentDescription("arrow"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                0),
                        isDisplayed()));
        imageView10.check(matches(isDisplayed()));
*/
        ViewInteraction textView26 = onView(
                allOf(withId(R.id.expandableTitle), withText("Kommentare"),
                        childAtPosition(
                                allOf(withId(R.id.head),
                                        childAtPosition(
                                                withId(R.id.expand_back),
                                                0)),
                                1),
                        isDisplayed()));
        textView26.check(matches(isDisplayed()));

        ViewInteraction relativeLayout16 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                6)),
                                0),
                        isDisplayed()));
        relativeLayout16.check(matches(isDisplayed()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.comment_first_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.body_comment),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction imageView11 = onView(
                allOf(withId(R.id.send_first_comment), withContentDescription("Senden"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.body_comment),
                                        0),
                                1),
                        isDisplayed()));
        imageView11.check(matches(isDisplayed()));

        relativeLayout9 = onView(
                allOf(withId(R.id.head),
                        childAtPosition(
                                allOf(withId(R.id.expand_back),
                                        childAtPosition(
                                                withId(R.id.eventBody),
                                                6)),
                                0)));
        relativeLayout9.perform(scrollTo(), click());

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
