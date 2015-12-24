package com.ada.memorygame.presenters;

import com.ada.memorygame.R;
import com.ada.memorygame.fragments.GameFragment;

import java.util.Random;

/**
 * This presenter is responsible about data operations and logic related with the game.
 * Created by Cagdas Direk on 20/12/15.
 */
public class GameFragmentPresenter {

    /**
     * Base view of the presenter.
     */
    private final GameFragment view;

    /**
     * Number of selected cards.
     */
    private int numSelected = 0;

    /**
     * Current score of the user.
     */
    private int score = 0;

    /**
     * Duplicated card array.
     */
    private Integer[] cardIds = {
            R.drawable.colour1, R.drawable.colour2,
            R.drawable.colour3, R.drawable.colour4,
            R.drawable.colour5, R.drawable.colour6,
            R.drawable.colour7, R.drawable.colour8,
            R.drawable.colour1, R.drawable.colour2,
            R.drawable.colour3, R.drawable.colour4,
            R.drawable.colour5, R.drawable.colour6,
            R.drawable.colour7, R.drawable.colour8
    };

    /**
     * Number of matched pairs.
     */
    private int numMatched = 0;

    /**
     * Constructor of the presenter.
     *
     * @param view The main view of the current fragment.
     */
    public GameFragmentPresenter(GameFragment view) {
        this.view = view;
    }

    /**
     * Game initialize step, each time user reach the game fragment, this method should be called.
     */
    public void initGame() {
        randomiseArray();
    }

    /**
     * Shuffle the cards
     */
    private void randomiseArray() {
        Random rnd = new Random();
        for (int i = cardIds.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = cardIds[index];
            cardIds[index] = cardIds[i];
            cardIds[i] = a;
        }
    }

    /**
     * Return the cards of the game.
     *
     * @return cards array of the game.
     */
    public Integer[] getCards() {
        return cardIds;
    }

    /**
     * When a card is selected from view by user, this method will manage the round.
     */
    public void onCardSelected() {
        numSelected++;
        if (numSelected == 2) {
            view.onRoundFinished();
            numSelected = 0;
        }
    }

    /**
     * This method calculates the score of the user.
     *
     * @param isMatched This boolean determines the user win or lose the round.
     * @return the latest score of the user.
     */
    public int onRoundFinished(boolean isMatched) {
        if (isMatched) {
            score = score + 2;
            numMatched++;
        } else {
            score = score - 1;
        }

        if (numMatched == (cardIds.length / 2)) {
            gameFinished();
        }
        return score;
    }

    /**
     * Returns the score of the user.
     *
     * @return value of the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * This method will be triggered when the game finished.
     */
    private void gameFinished() {
        view.showResultPopup(score);
    }
}
