package com.ada.memorygame.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.ada.memorygame.callbacks.OnFragmentCompletedListener;

/**
 * Base fragment that keeps the common operations for the fragments.
 * Created by Cagdas Direk on 20/12/15.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Fragment listener for triggering the onFinish event to parent activity.
     */
    protected OnFragmentCompletedListener fragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentListener = (OnFragmentCompletedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    /**
     * This method should be called by any fragment while closing it.
     * If necessary, that can be override.
     */
    protected void onFinish() {
    }

    /**
     * Unique identifier of the each fragment.
     */
    public enum Fragment {
        GAME,
        SCORE
    }
}
