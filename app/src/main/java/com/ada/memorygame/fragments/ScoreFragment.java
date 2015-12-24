package com.ada.memorygame.fragments;

import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ada.memorygame.R;
import com.ada.memorygame.presenters.ScoreFragmentPresenter;

/**
 * Score screen of the game.
 * Created by Cagdas Direk on 20/12/15.
 */
public class ScoreFragment extends BaseFragment {

    /**
     * Singleton instance of the fragment.
     */
    private static ScoreFragment instance;

    /**
     * Instance of the presenter class.
     */
    protected ScoreFragmentPresenter presenter;

    /**
     * List view for the high score list.
     */
    private ListView list;

    /**
     * This method provides singleton instance of the fragment.
     *
     * @return the current instance of the fragment.
     */
    public static ScoreFragment getInstance() {
        if (instance == null) {
            instance = new ScoreFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        presenter = new ScoreFragmentPresenter(this);
    }

    /**
     * Initialize the required fields in the fragments.
     *
     * @param view Base view of the fragment.
     */
    private void initViews(View view) {
        list = (ListView) view.findViewById(R.id.high_score_list);
    }

    /**
     * Set the adapter of the list view that prepared in the presenter.
     *
     * @param adapter of the List view.
     */
    public void setListAdapter(SimpleCursorAdapter adapter) {
        list.setAdapter(adapter);
    }

    /**
     * When the all operations related with this fragment finished, this method will inform the
     * parent activity.
     */
    @Override
    public void onFinish() {
        instance = null;
        fragmentListener.onSuccess(Fragment.SCORE, null);
    }
}
