package com.ada.memorygame.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ada.memorygame.R;

/**
 * Extended SimpleCursorAdapter for doing some custom operations related with UI.
 * Created by Cagdas Direk on 24/12/15.
 */
public class HighScoreAdapter extends SimpleCursorAdapter {

    /**
     * Layout inflater to inflate the custom layout that we created.
     */
    private final LayoutInflater inflater;

    /**
     * Reference layout of the adapter.
     */
    private final int layout;

    public HighScoreAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.layout = layout;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView rank = (TextView) view.findViewById(R.id.rank);
        rank.setText(String.valueOf(cursor.getPosition() + 1));
    }
}
