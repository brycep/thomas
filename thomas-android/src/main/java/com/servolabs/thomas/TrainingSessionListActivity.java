package com.servolabs.thomas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;
import com.servolabs.thomas.domain.TrainingSession;

/**
 * Displays a list of training sessions. If the device is sufficiently large, the screen will display in two panes, 
 * where selecting a training session from the list will display detailed information in the second pane.
 */
public class TrainingSessionListActivity extends FragmentActivity implements TrainingSessionListFragment.Callbacks {

    private FrameLayout detailContainer; // Added for unit tests

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingsession_list);

        if (isTwoPaneLayout()) {
            ((TrainingSessionListFragment) getSupportFragmentManager().findFragmentById(R.id.trainingsession_list))
                            .setActivateOnItemClick(true);
        }
    }

    /**
     * This method replaces the ADT-generated mTwoPane instance field, to better support unit testing.
     */
    private boolean isTwoPaneLayout() {
        return getTrainingDetailContainer() != null;
    }

    private FrameLayout getTrainingDetailContainer() {
        if (detailContainer == null) {
            detailContainer = (FrameLayout) findViewById(R.id.trainingsession_detail_container);
        }
        return detailContainer;
    }

    @Override
    public void onItemSelected(TrainingSession trainingSession) {
        if (isTwoPaneLayout()) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(TrainingSessionDetailFragment.ARG_ITEM_ID, trainingSession);
            TrainingSessionDetailFragment fragment = new TrainingSessionDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.trainingsession_detail_container, fragment)
                            .commit();

        } else {
            Intent detailIntent = new Intent(this, TrainingSessionDetailActivity.class);
            detailIntent.putExtra(TrainingSessionDetailFragment.ARG_ITEM_ID, trainingSession);
            startActivity(detailIntent);
        }
    }

    /**
     * Added to support unit test
     */
    void setTrainingSessionDetailContainer(FrameLayout detailContainer) {
        this.detailContainer = detailContainer;
    }
}
