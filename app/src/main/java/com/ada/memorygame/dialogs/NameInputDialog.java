package com.ada.memorygame.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ada.memorygame.R;
import com.ada.memorygame.callbacks.OnDialogCompletedListener;
import com.ada.memorygame.fragments.BaseFragment;
import com.ada.memorygame.fragments.GameFragment;

/**
 * When the user finished the game, you will see this dialog for saving the user info to db.
 * Created by Cagdas Direk on 22/12/15.
 */
public class NameInputDialog extends DialogFragment {

    /**
     * Key for arguments bundle.
     */
    public static final String SCORE = "SCORE";

    /**
     * Score of the user.
     */
    private int score;

    /**
     * Input area for the name of the user.
     */
    private EditText name;

    /**
     * Callback for moving the user name to the parent fragment.
     */
    private OnDialogCompletedListener callback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (OnDialogCompletedListener) (getFragmentManager().findFragmentByTag(BaseFragment.Fragment.GAME.name()));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDialogCompletedListener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View baseView = inflater.inflate(R.layout.dialog_name, null);
        builder.setView(baseView);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        extractArguments();
        initViews(baseView);
        return dialog;
    }

    /**
     * This method initializes the views in the dialog.
     */
    private void initViews(View baseView) {
        TextView scoreMessage = (TextView) baseView.findViewById(R.id.score_message);
        scoreMessage.setText(String.format(getResources().getString(R.string.score_message), score));

        Button button = (Button) baseView.findViewById(R.id.button_ok);
        button.setOnClickListener(onClick());

        name = (EditText) baseView.findViewById(R.id.input_name);
    }

    /**
     * This method extracts the arguments in the bundle of the dialog.
     */
    private void extractArguments() {
        Bundle args = getArguments();
        if (args != null) {
            score = args.getInt(SCORE);
        }
    }

    /**
     * Check if the field is empty or not
     *
     * @return true if the field is not empty, otherwise false
     */
    private boolean isValid() {
        if (name.getText().toString().isEmpty()) {
            name.setError(getResources().getString(R.string.name));
            return false;
        }

        return true;
    }

    /**
     * Dialog button on click listener.
     *
     * @return a new instance of {@link android.view.View.OnClickListener}
     */
    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValid()) {
                    return;
                }

                if (callback != null) {
                    callback.onSuccess(name.getText().toString());
                    ((GameFragment) getFragmentManager().findFragmentByTag(BaseFragment.Fragment.GAME.name())).onFinish();
                    dismiss();
                }
            }
        };
    }
}