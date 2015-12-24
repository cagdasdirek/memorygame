package com.ada.memorygame.fragments;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.ada.memorygame.R;
import com.ada.memorygame.adapters.CardAdapter;
import com.ada.memorygame.callbacks.OnDialogCompletedListener;
import com.ada.memorygame.database.DatabaseHandler;
import com.ada.memorygame.database.ScoreProvider;
import com.ada.memorygame.dialogs.NameInputDialog;
import com.ada.memorygame.modals.Card;
import com.ada.memorygame.modals.Score;
import com.ada.memorygame.presenters.GameFragmentPresenter;

import java.util.ArrayList;

/**
 * Game fragment of the application. This fragment is responsible for the game view.
 * Created by Cagdas Direk on 20/12/15.
 */
public class GameFragment extends BaseFragment implements OnDialogCompletedListener {

    /**
     * Default delay value for showing a matched pair to user.
     */
    private static final int DELAY_SUCCESS = 300;

    /**
     * Default delay value for showing a unmatched pair to user.
     */
    private static final int DELAY_FAILED = 1000;

    /**
     * Singleton instance of the fragment.
     */
    private static GameFragment instance;

    /**
     * Instance of the presenter class.
     */
    protected GameFragmentPresenter presenter;

    /**
     * Handler for post delay operations.
     */
    private final Handler handler = new Handler();

    /**
     * Grid view for the game.
     */
    public GridView gameGrid;

    /**
     * High score button in this fragment.
     */
    public Button highScore;

    /**
     * Current score of the user.
     */
    public TextView score;

    /**
     * Array for keeping the last selected pair by user.
     */
    private ArrayList<Card> selectedPair = new ArrayList<>(2);

    /**
     * This method provides singleton instance of the fragment.
     *
     * @return the current instance of the fragment.
     */
    public static GameFragment getInstance() {
        if (instance == null) {
            instance = new GameFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new GameFragmentPresenter(this);
        presenter.initGame();

        initViews(view);
    }

    /**
     * Initialize the required fields in the fragments.
     *
     * @param view Base view of the fragment.
     */
    private void initViews(View view) {
        score = (TextView) view.findViewById(R.id.score);
        score.setText(String.format(getResources().getString(R.string.score), 0));
        highScore = (Button) view.findViewById(R.id.high_score);
        highScore.setOnClickListener(onClick());
        gameGrid = (GridView) view.findViewById(R.id.game_grid);
        gameGrid.setAdapter(new CardAdapter(getActivity(), presenter.getCards()));
        gameGrid.setOnItemClickListener(onCardSelected());
    }

    /**
     * Grid view item selected listener.
     *
     * @return a new instance of the listener.
     */
    private AdapterView.OnItemClickListener onCardSelected() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) view;
                if (!card.isSelected()) {
                    card.setImageResource(card.getRealFace());
                    card.setIsSelected(true);
                    selectedPair.add(card);
                    presenter.onCardSelected();
                }
            }
        };
    }

    /**
     * When user selected a pair, this method finishes the round.
     */
    public void onRoundFinished() {
        gameGrid.setOnItemClickListener(null);
        showResult(isMatched() ? DELAY_SUCCESS : DELAY_FAILED);
    }

    /**
     * When the user selected a pair, this method shows them to user. Show time is different
     * for a successful match and failed one and this int value also determines the state of
     * the cards.
     *
     * @param showTime int value of presenting the selected pair to user.
     */
    private void showResult(final int showTime) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toggleCard(showTime == DELAY_SUCCESS);
                gameGrid.setOnItemClickListener(onCardSelected());
            }
        }, showTime);
    }

    /**
     * This method compares the two selected items.
     *
     * @return true, if the selected pair has been matched.
     */
    private boolean isMatched() {
        return selectedPair.get(0).getRealFace() == selectedPair.get(1).getRealFace();
    }

    /**
     * This method determines the state of the cards with the required parameter. If the
     * parameter is true, this method removes the cards from grid otherwise flip the background
     * of them.
     *
     * @param isMatched true if the cards are matching.
     */
    private void toggleCard(boolean isMatched) {
        for (Card card : selectedPair) {
            if (isMatched) {
                card.setVisibility(View.INVISIBLE);
                card.setIsSelected(false);
                card.setEnabled(false);
            } else {
                card.setImageResource(Card.CLOSED_FACE);
                card.setIsSelected(false);
            }
        }
        selectedPair.clear();
        score.setText(String.format(getResources().getString(R.string.score), presenter.onRoundFinished(isMatched)));
    }

    /**
     * This method will draw a popup and show the user the score and will ask the name of the user
     * to record it to high score table.
     *
     * @param score Finial score of the user.
     */
    public void showResultPopup(int score) {
        Bundle args = new Bundle();
        args.putInt(NameInputDialog.SCORE, score);

        NameInputDialog dialog = new NameInputDialog();
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "");
    }

    /**
     * On click listener for the High Scores button in this fragment.
     *
     * @return new instance of {@link android.view.View.OnClickListener}
     */
    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinish();
            }
        };
    }

    /**
     * When the all operations related with this fragment finished, this method will inform the
     * parent activity.
     */
    @Override
    public void onFinish() {
        instance = null;
        fragmentListener.onSuccess(Fragment.GAME, null);
    }

    @Override
    public void onSuccess(String name) {
        ContentValues highScore = new ContentValues();
        highScore.put(Score.NAME.toString(), name);
        highScore.put(Score.SCORE.toString(), presenter.getScore());
        getActivity().getContentResolver().insert(Uri.withAppendedPath(ScoreProvider.CONTENT_URI, DatabaseHandler.TABLE_SCORE), highScore);
    }
}