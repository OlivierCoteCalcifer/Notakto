package cstjean.mobile.notakto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test pour verifier la fonctionnabilité du SingletonNotakto.
 */
public class TestSingletonNotakto {
    /**
     * Variable du SingletonNotakto pour accéder à la logique du jeu.
     */
    private SingletonNotakto notakto;

    /**
     * Methode Before pour aller chercher l'instance du SingletonNotakto.
     */
    @Before
    public void setUp() {
        notakto = SingletonNotakto.getInstance();
        notakto.reinitialiser();
    }

    /**
     * Ces tests verifie les tour des joueurs.
     */
    @Test
    public void tourJoueur() {
        assertTrue(notakto.isTourJoueur1());
        notakto.jouerCoup(0, 1);
        assertFalse(notakto.isTourJoueur1());
    }

    /**
     * Verifie si la partie terminee apres une combinaison gagnante.
     */

    @Test
    public void testPartieTerminee() {
        notakto.jouerCoup(0, 0);
        notakto.jouerCoup(1, 1);
        notakto.jouerCoup(2, 2);
        notakto.jouerCoup(0, 1);
        assertEquals(' ', notakto.getLayoutNotakto()[0][1]);
        notakto.reinitialiser();
        notakto.jouerCoup(0, 0);
        notakto.jouerCoup(1, 2);
        notakto.jouerCoup(1, 1);
        notakto.jouerCoup(1, 0);
        notakto.jouerCoup(2, 0);
        assertEquals(' ', notakto.getLayoutNotakto()[2][0]);
    }

    /**
     * Verifie l'affichage des tours de joueurs ainsi que le message pour le joueur gagnant.
     */
    @Test
    public void testAffichage() {
        notakto.jouerCoup(0, 0);
        notakto.jouerCoup(1, 1);
        notakto.jouerCoup(2, 2);
        assertEquals("Félicitations! \n Le joueur 2 a gagné!!!",
                notakto.updateTextAndTurn());
        assertEquals("Joueur 1 a perdu...", notakto.messagePerdant());
        notakto.reinitialiser();
        notakto.jouerCoup(0, 0);
        notakto.jouerCoup(1, 2);
        notakto.jouerCoup(1, 1);
        notakto.jouerCoup(1, 0);
        assertEquals("Félicitations! \n Le joueur 1 a gagné!!!",
                notakto.updateTextAndTurn());
        assertEquals("Joueur 2 a perdu...", notakto.messagePerdant());
        notakto.reinitialiser();
        assertEquals("Au tour du joueur 1", notakto.updateTextAndTurn());
        notakto.jouerCoup(0, 0);
        assertEquals('X', notakto.getLayoutNotakto()[0][0]);
        assertEquals("Au tour du joueur 2", notakto.updateTextAndTurn());
        notakto.jouerCoup(2, 2);
        assertEquals('X', notakto.getLayoutNotakto()[2][2]);
        assertEquals("Au tour du joueur 1", notakto.updateTextAndTurn());
        notakto.reinitialiser();
        notakto.jouerCoup(0, 0);
        notakto.jouerCoup(1, 2);
        notakto.jouerCoup(1, 1);
        notakto.jouerCoup(1, 0);
        assertTrue(notakto.isPartieTerminee());
    }

    /**
     * Verifie si la reinitialisation du jeu Notakto.
     */
    @Test
    public void reinitialisation() {
        notakto.jouerCoup(0, 0);
        notakto.jouerCoup(0, 1);
        notakto.reinitialiser();
        assertEquals(' ', notakto.getLayoutNotakto()[0][0]);
        assertEquals(' ', notakto.getLayoutNotakto()[0][1]);
    }

    /**
     * Verifie les combinaisons gagnantes du Notakto.
     */
    @Test
    public void testCombinaisonGagnante() {
        // Ligne horizontale en haut
        notakto.jouerCoup(0, 0);
        notakto.jouerCoup(0, 1);
        notakto.jouerCoup(0, 2);
        assertTrue(notakto.isPartieTerminee());

        // Ligne horizontale en centre
        notakto.reinitialiser();
        notakto.jouerCoup(1, 0);
        notakto.jouerCoup(1, 1);
        notakto.jouerCoup(1, 2);
        assertTrue(notakto.isPartieTerminee());

        // Ligne horizontale en bas
        notakto.reinitialiser();
        notakto.jouerCoup(2, 0);
        notakto.jouerCoup(2, 1);
        notakto.jouerCoup(2, 2);
        assertTrue(notakto.isPartieTerminee());

        // Ligne verticale a gauche
        notakto.reinitialiser();
        notakto.jouerCoup(0, 0);
        notakto.jouerCoup(1, 0);
        notakto.jouerCoup(2, 0);
        assertTrue(notakto.isPartieTerminee());

        // Ligne verticale a centre
        notakto.reinitialiser();
        notakto.jouerCoup(0, 1);
        notakto.jouerCoup(1, 1);
        notakto.jouerCoup(2, 1);
        assertTrue(notakto.isPartieTerminee());

        // Ligne verticale a droite
        notakto.reinitialiser();
        notakto.jouerCoup(0, 2);
        notakto.jouerCoup(1, 2);
        notakto.jouerCoup(2, 2);
        assertTrue(notakto.isPartieTerminee());
        // Diagonale En haut a gauche a en bas a droite
        notakto.reinitialiser();
        notakto.jouerCoup(0, 0);
        notakto.jouerCoup(1, 1);
        notakto.jouerCoup(2, 2);
        assertTrue(notakto.isPartieTerminee());

        // Diagonale En bas a gauche a en haut a droite
        notakto.reinitialiser();
        notakto.jouerCoup(2, 0);
        notakto.jouerCoup(1, 1);
        notakto.jouerCoup(0, 2);
        assertTrue(notakto.isPartieTerminee());
    }

    /**
     * Verifie si deux instances sont identitiques.
     */
    @Test
    public void testSingletonNotakto() {
        SingletonNotakto singleton2 = SingletonNotakto.getInstance();
        assertSame(notakto, singleton2);
    }
}
