package com.ada.memorygame.presenters;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

import com.ada.memorygame.R;
import com.ada.memorygame.adapters.HighScoreAdapter;
import com.ada.memorygame.database.ScoreProvider;
import com.ada.memorygame.fragments.ScoreFragment;
import com.ada.memorygame.modals.Score;

/**
 * Score presenter calls. This will handle the population of the score fragment logic.
 * Created by Cagdas Direk on 23/12/15.
 */
public class ScoreFragmentPresenter implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Base view of the presenter.
     */
    private final ScoreFragment view;

    /**
     * This is the Adapter being used to display the list's data
     */
    SimpleCursorAdapter adapter;

    /**
     * These are the Contacts rows that we will retrieve
     */
    static final String[] PROJECTION = new String[]{
            Score._ID.toString(),
            Score.NAME.toString(),
            Score.SCORE.toString()
    };

    /**
     * Constructor of the presenter.
     *
     * @param view The main view of the current fragment.
     */
    public ScoreFragmentPresenter(ScoreFragment view) {
        this.view = view;

        String[] fromColumns = {Score.NAME.toString(), Score.SCORE.toString()};
        int[] toViews = {R.id.name, R.id.score};

        adapter = new HighScoreAdapter(view.getActivity(), R.layout.list_view_item, null, fromColumns, toViews, 0);
        view.setListAdapter(adapter);
        view.getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(view.getActivity(), ScoreProvider.CONTENT_URI, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
