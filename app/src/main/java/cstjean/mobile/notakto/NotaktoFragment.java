package cstjean.mobile.notakto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment pour l'affichage du Notakto.
 */
public class NotaktoFragment extends Fragment implements Buttons.NotaktoGameListener {

    /**
     * Array pour rassembler les boutons sur le jeu Notakto.
     */
    private final Button[] buttonsArray = new Button[10];
    /**
     * Le textView est pour l'affichage du tour du joueur.
     */
    private TextView textView;
    /**
     * Le SingletonNotakto avec la logique du jeu.
     */
    private final SingletonNotakto notakto = SingletonNotakto.getInstance();

    /**
     * Methode pour aller chercher l'ancienne state au besoin.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Instance de creation de la view du notakto.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return view              Retourne la vue du notakto.
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
        startGame(view);
    }

    private void setUpBoardView(View view) {
        LinearLayout layoutMain = view.findViewById(R.id.layout_LinearLayoutMain);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        LinearLayout layoutNotaktoRangee1 = view.findViewById(R.id.layout_LinearLayoutRangee1);
        LinearLayout layoutNotaktoRangee2 = view.findViewById(R.id.layout_LinearLayoutRangee2);
        LinearLayout layoutNotaktoRangee3 = view.findViewById(R.id.layout_LinearLayoutRangee3);
        for (int i = 0; i < 4; i++) {
            LinearLayout rowLayout = new LinearLayout(getContext());
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            if (i < 3) {
                // On créer la boutons pour les boutons.
                for (int j = 0; j < 3; j++) {
                    Button button = new Button(getContext());
                    int buttonIndex = i * 3 + j;
                    buttonsArray[buttonIndex] = button;
                    button.setLayoutParams(params);
                    switch (i){
                        case 0 : layoutNotaktoRangee1.addView(button);
                        case 1 : layoutNotaktoRangee2.addView(button);
                        case 2 : layoutNotaktoRangee3.addView(button);
                    }
                    rowLayout.addView(button);
                }
                layoutMain.addView(rowLayout);
            } else {
                Button button = new Button(getContext());
                int buttonIndex = 9;
                buttonsArray[buttonIndex] = button;
                button.setLayoutParams(params);
                rowLayout.addView(button);

            }
        }
    }

    /**
     * Cette methode est la "Game Loop" pour le notakto. On appelle joueurCoup de la classe Notakto
     * pour valider les mouvements en plus de verifier si une combinaison gagnante est accompli.
     */
    private void startGame(View view) {
        for (int i = 0; i < buttonsArray.length; i++) {
            final int index = i;
            buttonsArray[i].setOnClickListener(v -> {
                onButtonClick(index);
            });
        }

        /*Button resetButton = view.findViewById(R.id.btnRestart);
        resetButton.setOnClickListener(v -> onButtonReset());*/
    }

    public void onButtonReset() {
        notakto.reinitialiser();
        for (int j = 1; j <= 9; j++) {
            buttonsArray[j].setText(notakto.CASE_NON_COCHE);
        }
        textView.setText(notakto.updateTextAndTurn());

    }

    public void onButtonClick(int finalI) {
        int ligne = (finalI - 1) / 3;
        int colonne = (finalI - 1) % 3;
        if (notakto.jouerCoup(ligne, colonne)) {
            buttonsArray[finalI].setText(notakto.CASE_COCHE);
            textView.setText(notakto.updateTextAndTurn());

        }
    }
}