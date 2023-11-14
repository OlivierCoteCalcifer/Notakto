package cstjean.mobile.notakto;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment pour l'affichage du Notakto.
 */
public class NotaktoFragment extends Fragment {

    /**
     * Array pour rassembler les boutons sur le jeu Notakto.
     */
    private final Button[] buttonsArray = new Button[9]; // Corrected size to 9 for a 3x3 grid
    /**
     * Le textView est pour l'affichage du tour du joueur.
     */
    private TextView textView;
    /**
     * Le SingletonNotakto avec la logique du jeu.
     */
    private final SingletonNotakto notakto = SingletonNotakto.getInstance();
    /**
     * Cette variable est pour une condition du Toast.
     */
    private int compteurToast = 0;

    /**
     * Cette methode crée la vue avec les boutons.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return la vue du notakto
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notakto, container, false);
        setUpBoardView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            for (int i = 0; i < buttonsArray.length; i++) {
                String buttonText = savedInstanceState.getString("Button" + i);
                buttonsArray[i].setText(buttonText);
            }
        }
        startGame(view);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of your buttons (e.g., which ones are marked 'X')
        for (int i = 0; i < buttonsArray.length; i++) {
            outState.putString("Button" + i, buttonsArray[i].getText().toString());
        }
    }

    /**
     * Cette méthode ajoute dynamiquement les boutons, tout en allant chercher les LinearLayout
     * nécessaires pour le jeu.
     *
     * @param view Vue du notakto.
     */
    private void setUpBoardView(View view) {
        LinearLayout layoutMain = view.findViewById(R.id.layout_LinearLayoutMain);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        layoutMain.setGravity(Gravity.CENTER);
        // Ajout du textview pour le tour des joueurs.
        textView = new TextView(getContext());
        textView.setText(notakto.updateTextAndTurn());
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // Width
                LinearLayout.LayoutParams.WRAP_CONTENT  // Height
        ));

        textView.setGravity(Gravity.CENTER); // Center text horizontally
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32); // Set text size to 24sp
        textView.setTypeface(null, Typeface.BOLD); // Make the text bold
        layoutMain.addView(textView, 0);

        // Recuperation des layout dans le fragment pour l'ajout dynamique des boutons.
        LinearLayout layoutNotaktoRangee1 = view.findViewById(R.id.layout_LinearLayoutRangee1);
        LinearLayout layoutNotaktoRangee2 = view.findViewById(R.id.layout_LinearLayoutRangee2);
        LinearLayout layoutNotaktoRangee3 = view.findViewById(R.id.layout_LinearLayoutRangee3);

        // Boucle pour l'ajout dynamique des boutons.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button(getContext());
                int buttonIndex = i * 3 + j;
                buttonsArray[buttonIndex] = button;
                button.setLayoutParams(params);
                switch (i) {
                    case 0:
                        layoutNotaktoRangee1.addView(button);
                        break;
                    case 1:
                        layoutNotaktoRangee2.addView(button);
                        break;
                    case 2:
                        layoutNotaktoRangee3.addView(button);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Cette méthode ajoute les "OnClickListener" pour chaque bouton du jeu.
     *
     * @param view Vue du notakto.
     */
    private void startGame(View view) {
        for (int i = 0; i < buttonsArray.length; i++) {
            final int index = i;
            buttonsArray[i].setOnClickListener(v -> {
                onButtonClick(index);
            });
        }

        Button resetButton = view.findViewById(R.id.btnRestart);
        resetButton.setOnClickListener(v -> onButtonReset());
    }

    /**
     * Cette méthode redémarre une nouvelle partie lorsque le bouton Réinitialiser est appuyer.
     */
    public void onButtonReset() {
        notakto.reinitialiser();
        for (int j = 0; j < 9; j++) { // Start from 0
            buttonsArray[j].setText(String.valueOf((notakto.CASE_NON_COCHE)));
        }
        textView.setText(notakto.updateTextAndTurn());
        compteurToast = 0;
    }

    /**
     * Cette méthode ajoute X au bouton appuyé du notakto et si la partie est terminée on affiche
     * un toast pour le joueur perdant.
     *
     * @param buttonIndex Index du bouton du notakto dans buttonArray.
     */
    public void onButtonClick(int buttonIndex) {
        int ligne = buttonIndex / 3;
        int colonne = buttonIndex % 3;
        if (notakto.jouerCoup(ligne, colonne)) {
            buttonsArray[buttonIndex].setText(String.valueOf(notakto.CASE_COCHE));
            textView.setText(notakto.updateTextAndTurn());
        }
        if (notakto.isPartieTerminee() && compteurToast == 0) {
            compteurToast++;
            Toast.makeText(getContext(), notakto.messagePerdant(), Toast.LENGTH_LONG).show();
        }
    }
}
