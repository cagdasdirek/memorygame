package com.ada.memorygame;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ada.memorygame.callbacks.OnFragmentCompletedListener;
import com.ada.memorygame.fragments.BaseFragment;
import com.ada.memorygame.fragments.GameFragment;
import com.ada.memorygame.fragments.ScoreFragment;

public class MainActivity extends AppCompatActivity implements OnFragmentCompletedListener {

    /**
     * Initial onCreate method of the activity.
     *
     * @param savedInstanceState We should pass this saved instance to handle configuration changes.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFragment(GameFragment.getInstance(), BaseFragment.Fragment.GAME);
    }

    /**
     * Whenever user click the back button, we should handle this event to provide correct
     * navigation between screens to user.
     */
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
            return;
        }

        super.onBackPressed();
    }

    /**
     * This method opens the passed fragment.
     *
     * @param fragment that we would like to present to user.
     */
    private void openFragment(BaseFragment fragment, BaseFragment.Fragment name) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_holder, fragment, name.name());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Whenever a fragment completed its own job, it triggers this callback and this callback
     * provides the navigation logic.
     *
     * @param fragmentName {@link com.ada.memorygame.fragments.BaseFragment.Fragment}
     * @param something    If there is extra information available for the fragment, it can be
     *                     passed to other one.
     */
    @Override
    public void onSuccess(BaseFragment.Fragment fragmentName, Object something) {
        switch (fragmentName) {
            case GAME:
                openFragment(ScoreFragment.getInstance(), BaseFragment.Fragment.SCORE);
                break;
            case SCORE:
                break;
        }
    }
}