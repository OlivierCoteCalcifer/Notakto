
package cstjean.mobile.notakto;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * Cette classe étend AppCompatButton pour permettre la création de boutons de manière dynamique dans le layout.
 */
public class Buttons extends AppCompatButton implements View.OnClickListener {
    /**
     * Variable clickListener.
     */
    private OnClickListener notaktoClickListener;
    /**
     * Index du bouton.
     */
    private int buttonIndex;

    /**
     * Constructeur utilisé pour créer le bouton programmatically (dynamiquement) sans attributs de style.
     *
     * @param context Le contexte de l'application, nécessaire pour la création de la vue.
     */
    public Buttons(Context context) {
        super(context);
        setOnClickListener(this);
    }

    /**
     * Constructeur utilisé pour l'inflation depuis un fichier XML, permettant de passer des attributs de style.
     *
     * @param context Le contexte de l'application, nécessaire pour la création de la vue.
     * @param attrs   Les attributs de style spécifiés dans le fichier XML.
     */
    public Buttons(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        this.buttonIndex = buttonIndex;
    }
    /**
     * Méthode pour assigner un évènement au Notakto.
     *
     * @param view La vue dynamique.
     */
    @Override
    public void onClick(View view) {
        if (notaktoClickListener != null) {
            if (buttonIndex == 0){
                notaktoClickListener.onClick(view);
            } else{
               notaktoClickListener.onClick(view);
            }
        }
    }

    public interface NotaktoGameListener {
        void onButtonReset();

        void onButtonClick(int buttonIndex);
    }
}

