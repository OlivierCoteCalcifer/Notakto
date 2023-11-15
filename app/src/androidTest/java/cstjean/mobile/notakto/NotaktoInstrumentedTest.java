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
    /**
     * On va chercher le activity du Notakto pour les tests instrumentaux.
     */
    @Rule
    public ActivityScenarioRule<NotaktoActivity> rule = new ActivityScenarioRule<>(NotaktoActivity.class);
    /**
     * Cette variable est le caractère lorsque le bouton est appuyé.
     */
    private final String charResultat = "X";
    /**
     * Cette variable est le caractère lorsque le bouton n'est pas encore appuyé.
     */
    private final String charEmpty = " ";
    /**
     * Cette variable est le ID pour le textView qui affiche le tour des joueurs.
     */
    private final String textId = "textTurn";
    /**
     * Cette variable est le ID pour le textView qui affiche le message du perdant.
     * Le message est habituellement dans un Toast.
     */
    private final String textIdPerdant = "textPerdant";
    /**
     * Cette variable est le message dans le textView pour le tour du joueur 1.
     */
    private final String msgTour1 = "Au tour joueur 1";
    /**
     * Cette variable est le message dans le textView pour le tour du joueur 2.
     */
    private final String msgTour2 = "Au tour joueur 2";
    /**
     * Cette variable est le message de victoire pour le joueur 1.
     */
    private final String msgGagnant1 = "Félicitations! \n Le joueur 1 a gagné!!!";
    /**
     * Cette variable est le message de victoire pour le joueur 2.
     */
    private final String msgGagnant2 = "Félicitations! \n Le joueur 2 a gagné!!!";
    /**
     * Cette variable est le message lorsque le joueur 1 perd la partie.
     */
    private final String msgPerdant1 = "Joueur 1 a perdu...";
    /**
     * Cette variable est le message lorsque le joueur 2 perd la partie.
     */
    private final String msgPerdant2 = "Joueur 2 a perdu...";
    /**
     * Cette variable est le ID du bouton 0.
     */
    private final String id0 = "btn0";
    /**
     * Cette variable est le ID du bouton 1.
     */
    private final String id1 = "btn1";
    /**
     * Cette variable est le ID du bouton 4.
     */
    private final String id4 = "btn4";
    /**
     * Cette variable est le ID du bouton 7.
     */
    private final String id7 = "btn7";
    /**
     * Cette variable est le ID du bouton 8.
     */
    private final String id8 = "btn8";

    /**
     * Cette methode verifie les messages gagnants et perdants du Notakto avec l'orientation.
     */
    @Test
    public void testAffichageJoueurGagnanteEtPerdant() throws RemoteException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.setOrientationNatural();

        // On verifie le message du gagnant joueur 1
        onView(withTagValue(is(id0))).perform(click());
        onView(withTagValue(is(id4))).perform(click());
        onView(withTagValue(is(id7))).perform(click());
        onView(withTagValue(is(id1))).perform(click());
        withText(msgGagnant1).matches(onView(withTagValue(is(textId))));
        withText(msgPerdant2).matches(onView(withTagValue(is(textIdPerdant))));

        // On change l'orientation et verifie que les messages sont toujours là.
        device.setOrientationLeft();
        withText(msgGagnant1).matches(onView(withTagValue(is(textId))));
        withText(msgPerdant2).matches(onView(withTagValue(is(textIdPerdant))));
        onView(withId(R.id.btnRestart)).perform((click()));

        // On verifie le message du gagnant joueur 2
        onView(withTagValue(is(id0))).perform(click());
        onView(withTagValue(is(id4))).perform(click());
        onView(withTagValue(is(id8))).perform(click());
        withText(msgGagnant2).matches(onView(withTagValue(is(textId))));
        withText(msgPerdant1).matches(onView(withTagValue(is(textIdPerdant))));
        onView(withId(R.id.btnRestart)).perform((click()));
    }

    /**
     * Cette methode verifie les messages des tours du Notakto lorsqu'on change
     * l'orientation de l'appareil et si les X suivent en changeant l'orientation.
     */
    @Test
    public void testAffichageChangementOrientation() throws RemoteException {
        // On appuie sur deux boutons et verifie si le X est dans le bouton et il suit
        // en changeant l'orientation de l'appareil de portrait à landscape, vice-versa.

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.setOrientationNatural();
        onView(withTagValue(is(id0))).perform(click());
        withText(charResultat).matches(onView(withTagValue(is(id0))));

        device.setOrientationLeft();
        withText(charResultat).matches(onView(withTagValue(is(id0))));

        // On appuie sur un bouton en landscape et verifie qu'il suit en portrait.
        onView(withTagValue(is(id7))).perform(click());
        withText(charResultat).matches(onView(withTagValue(is(id7))));
        withText(msgTour2).matches(onView(withTagValue(is(textId))));
        device.setOrientationNatural();
        withText(charResultat).matches(onView(withTagValue(is(id7))));
        withText(msgTour2).matches(onView(withTagValue(is(textId))));
        onView(withId(R.id.btnRestart)).perform((click()));
    }

    /**
     * Cette methode verifie le message de base pour les tours de joueurs ainsi que les X
     * lorsqu'on appuie sur un bouton.
     */
    @Test
    public void testAffichageBoutonCharNotakto() {

        // Verifie l'affichage du message dans le textView.
        withText(msgTour1).matches(onView(withTagValue(is(textId))));
        onView(withTagValue(is(id0))).perform(click());

        // Verifie si le X est bien placer et si le textView à changer pour le joueur 2.
        withText(charResultat).matches(onView(withTagValue(is(id0))));
        withText(msgTour2).matches(onView(withTagValue(is(textId))));

        // On repart la partie et verifie que tout est bien vide et que le textView est
        // pour le joueur 1.
        onView(withId(R.id.btnRestart)).perform((click()));
        withText(charEmpty).matches(onView(withTagValue(is(id0))));
        withText(msgTour1).matches(onView(withTagValue(is(id0))));
    }
}