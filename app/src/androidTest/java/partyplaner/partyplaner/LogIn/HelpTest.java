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
public class HelpTest {

    @Rule
    public ActivityTestRule<LogInActivity> mActivityTestRule = new ActivityTestRule<>(LogInActivity.class);

    @Test
    public void helpTest() {
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

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        7),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.Help), withText("Hilfe"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.HelpText), withText("Hilfe zum Umgang mit dem Partyplaner PaPla \n \nMit Hilfe von PaPla können Sie als Benutzer ihre eigene Veranstaltung planen, organisieren und Ihre Freunde zu der Party einladen. \n \nHome: \nAuf der Home Seite des PaPla werden ihnen die aktuell anstehenden Veranstaltungen angezeigt, die Sie erstellt haben oder zu denen sie eingeladen sind. Hier können sie zu jeder Party den Veranstalter, den Zeitpunkt und eine kleine Beschreibung ansehen. \n \nProfil: \nDer Bereich Profil zeigt ihnen die von ihnen angegebenen Daten an und gibt ihnen die Möglichkeit diese zu verwalten. Hier steht ihnen die Möglichkeit zur Verfügung ihren Profilnamen und ihre E-Mailadresse einzusehen, sowie ihr Geburtsdatum, Geschlecht und persönliches Profilbild anzeigen zu lassen. Hier werden ihnen auch Ihre vergangenen und zukünftigen Veranstaltungen als Übersicht angezeigt. \n \nKontakte: \nÜber Kontakte können sie ihre Kontakte verwalten und neue Kontakte hinzufügen. \n \nOptionen: \nÜber den Optionen Button in der rechten oberen Ecke können sie von überall aus auf ein Menü zugreifen, dass zum Partyersteller oder zur Profilbearbeitung führt. \n \nEigene Veranstaltungen: \nAuf dieser Seite werden ihnen Ihre eigenen Veranstaltungen angezeigt. \n \nVeranstaltung – Galerie: \nIn der Galerie können sie sich alle Bilder zu der Party anzeigen lassen und eigene Bilder hinzufügen. \n \nVeranstaltung - Aufgaben: \nIn dem Abschnitt Aufgaben können sie bestimmte Aufgaben an Gäste verteilen und dessen Status prüfen. \n \nVeranstaltung - ToDo-Liste: \nIn der ToDo-Liste können sie eigenen Aufgaben für sich selber eintragen und verwalten. \n \nVeranstaltung - Gäste: \nÜber den Abschnitt Gäste können sie Gäste einladen und einsehen, wer zu ihrer Party zu- oder abgesagt hat. \n \nVeranstaltung - Abstimmung: \nIn diesem Abschnitt können sie und ihre Gäste über einige Eigenschaften ihrer Party abstimmen, falls Unklarheiten zu ihrer Party entstehen. Als Beispiel: Welche Pizza wird gewünscht? \n \nVeranstaltung - Bewertung: \nIn diesem Abschnitt können sie und ihre Gäste ihre Party im Nachhinein bewerten. \n \nVeranstaltung - Kommentare: In diesem Abschnitt können sie und ihre Gäste über die Party schreiben und sich austauschen."),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

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
