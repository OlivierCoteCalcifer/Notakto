package cstjean.mobile.notakto;

/**
 * Classe SingletonNotakto.
 */
public class SingletonNotakto {
    /**
     * Instance de du Notakto.
     */
    private static SingletonNotakto instance = null;
    /**
     * Variable string lorsque qu'on appuie sur un bouton.
     */

    public static final char CASE_COCHE = 'X';
    /**
     * Variable string lorsque le bouton n'a pas encore été appuyé.
     */
    public static final char CASE_NON_COCHE = ' ';

    /**
     * Array 2D pour le layout du notakto.
     */
    private final char[][] layoutNotakto = new char[3][3]; // Assuming a 3x3 Notakto game board

    /**
     * Array 2D pour des combinaisons gagnante.
     */
    private final int[][] combinaisonGagnante = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 4, 8}, {2, 4, 6}, {0, 3, 6},
            {1, 4, 7}, {2, 5, 8}
    };

    /**
     * Boolean pour déterminer le tour des joueurs.
     */
    private boolean tourJoueur1;

    /**
     * Boolean pour déterminer si la partie est terminée.
     */
    private boolean partieTerminee;

    /**
     * Private constructor so no other class can instantiate it.
     */
    private SingletonNotakto() {
        reinitialiser();
    }

    /**
     * Public static method to get the instance.
     *
     * @return The Singleton instance of Notakto.
     */
    public static SingletonNotakto getInstance() {
        if (instance == null) {
            instance = new SingletonNotakto();
        }
        return instance;
    }

    /**
     * Getter pour le tourDuJoueur.
     *
     * @return Boolean pour le tour du joueur.
     */
    public boolean isTourJoueur1() {
        return tourJoueur1;
    }

    /**
     * Getter pour le boolean de la partie terminée.
     *
     * @return Boolean si la partie est terminée ou non.
     */
    public boolean isPartieTerminee() {
        return partieTerminee;
    }

    public char[][] getLayoutNotakto() {
        return layoutNotakto;
    }

    /**
     * Cette méthode vide l'array 2D du layout pour remplacer les X par des espaces.
     */
    void reinitialiser() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                layoutNotakto[i][j] = CASE_NON_COCHE;
            }
        }
        tourJoueur1 = true;
        partieTerminee = false;
    }

    /**
     * Cette methode verifie la partie en cours avec les combinaisons gagnante.
     *
     * @param partieEnCours La partie envoie un char [][] à la méthode et analyse avec l'array
     *                      gagnante.
     */
    public void verifCombinaisonGagnante(char[][] partieEnCours) {
        for (int[] combinaison : combinaisonGagnante) {
            if (estCombinaisonGagnante(partieEnCours, combinaison)) {
                partieTerminee = true;
                break;
            }
        }
    }

    /**
     * Cette méthode ajoute un X dans layoutNotakto on renvoie un boolean au MainActivity pour
     * valider le coup et changer le text dans le bouton.
     *
     * @param ligne   Ligne dans laquel le bouton a été appuyé.
     * @param colonne Colonne dans laquel le bouton a été appuyé.
     * @return Si les conditions sont remplis on envoie un true.
     */

    public boolean jouerCoup(int ligne, int colonne) {
        if (layoutNotakto[ligne][colonne] == ' ' && !partieTerminee) {
            layoutNotakto[ligne][colonne] = 'X';
            verifCombinaisonGagnante(layoutNotakto);
            if (!partieTerminee) {
                tourJoueur1 = !tourJoueur1;
            }
            return true;
        }
        return false;
    }

    /**
     * Cette méthode change le tour du joueur ainsi que le texte d'affichage pour dire à quel tour
     * on est rendu.
     *
     * @return String avec le message pour un textView.
     */
    public String updateTextAndTurn() {
        if (partieTerminee) {
            return tourJoueur1 ? "Félicitations! \n Le joueur 2 a gagné!!!" :
                    "Félicitations! \n Le joueur 1 a gagné!!!";
        }
        return tourJoueur1 ? "Au tour du joueur 1" : "Au tour du joueur 2";
    }

    /**
     * Cette methode renvoie le message pour le toast avec le message du joueur qui a perdu.
     *
     * @return String avec le message "Joueur X a perdu!.
     */
    public String messagePerdant() {
        if (tourJoueur1) {
            return "Joueur 1 a perdu...";
        } else {
            return "Joueur 2 a perdu...";
        }
    }

    /**
     * Verfie les combinaisons gagnante.
     *
     * @param layoutNotakto Recoit le layout de la partie
     * @param combinaison   Combinaisoin gagnante.
     * @return Si une séquence est complétée on retourne TRUE.
     */
    private boolean estCombinaisonGagnante(char[][] layoutNotakto, int[] combinaison) {
        for (int index : combinaison) {
            // Verifie l'index en divisant par 3 pour trouver la rangee et le modulo 3 pour
            // la colonne.
            if (layoutNotakto[index / 3][index % 3] != 'X') {
                return false;
            }
        }
        return true;
    }
}
