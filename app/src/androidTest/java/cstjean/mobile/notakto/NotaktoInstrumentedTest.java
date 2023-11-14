package cstjean.mobile.notakto;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


import static org.hamcrest.CoreMatchers.is;

import android.os.RemoteException;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Tests intrusmentées pour vérifier le fonctionnement graphique du Notakto.
 */
@RunWith(AndroidJUnit4.class)
public class NotaktoInstrumentedTest extends RemoteException {
    @Rule
    public ActivityScenarioRule<NotaktoActivity> rule = new ActivityScenarioRule<>(NotaktoActivity.class);

    @Test
    public void testResultat() throws RemoteException {
        String charResultat = "X";
        String id = "btn1";
        onView(withId(R.id.btnRestart)).perform((click()));
        onView(withTagValue(is((Object) id))).perform(click());
        withText(charResultat).matches(onView(withTagValue(is((Object) id))));
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.setOrientationLeft();

    }
}