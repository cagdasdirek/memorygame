package com.ada.memorygame.callbacks;

import com.ada.memorygame.fragments.BaseFragment;

/**
 * Container Activity must implement this interface
 * Created by Cagdas Direk on 23/12/15.
 */
public interface OnFragmentCompletedListener {
    void onSuccess(BaseFragment.Fragment fragmentName, Object something);
}
