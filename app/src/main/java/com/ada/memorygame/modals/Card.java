package com.ada.memorygame.modals;

import android.content.Context;
import android.widget.ImageView;

import com.ada.memorygame.R;

/**
 * Custom Card View modal extends from basic Android Image View
 * Created by Cagdas Direk on 20/12/15.
 */
public class Card extends ImageView {

    /**
     * Background resource of the cards.
     */
    public static final int CLOSED_FACE = R.drawable.card_bg;

    /**
     * State of the card. If it is selected, it will be true otherwise false.
     */
    private boolean isSelected;

    /**
     * Real face of the card.
     */
    private int realFace;

    /**
     * Constructor of the modal object.
     *
     * @param context of the application.
     */
    public Card(Context context) {
        super(context);
    }

    /**
     * Getter for isSelected
     *
     * @return state of the card.
     */
    @Override
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Setter for isSelected
     *
     * @param isSelected true if the user selected the card otherwise false
     */
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * Getter for the real face of the card.
     *
     * @return realFace value of the card.
     */
    public int getRealFace() {
        return realFace;
    }

    /**
     * Setter for the real face of the card.
     *
     * @param realFace This will be the real face of the card.
     */
    public void setRealFace(int realFace) {
        this.realFace = realFace;
    }
}
