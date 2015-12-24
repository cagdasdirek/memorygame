package com.ada.memorygame.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.ada.memorygame.modals.Card;

/**
 * View adapter for Card Grid View.
 * Created by Cagdas Direk on 20/12/15.
 */
public class CardAdapter extends BaseAdapter {

    /**
     * Context of the activity.
     */
    private final Context context;

    /**
     * Integer array of the card resource ids.
     */
    private final Integer[] cardIds;

    /**
     * Constructor of the class.
     *
     * @param context Context of the activity.
     * @param cardIds Integer array of the card resource ids.
     */
    public CardAdapter(Context context, Integer[] cardIds) {
        this.context = context;
        this.cardIds = cardIds;
    }

    @Override
    public int getCount() {
        return cardIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card;
        if (convertView == null) {
            card = new Card(context);
            card.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            card.setRealFace(cardIds[position]);
            card.setImageResource(Card.CLOSED_FACE);
        } else {
            card = (Card) convertView;
        }
        return card;
    }
}
