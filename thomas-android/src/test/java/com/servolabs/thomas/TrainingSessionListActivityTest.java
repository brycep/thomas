package com.servolabs.thomas;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import com.servolabs.robolectric.ThomasTestRunner;
import com.servolabs.thomas.domain.TrainingSession;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(ThomasTestRunner.class)
public class TrainingSessionListActivityTest {

    private TrainingSessionListActivity activity;

    private FrameLayout detailContainer;

    @Before
    public void setUpViews() {
        activity = new TrainingSessionListActivity();
        detailContainer = new FrameLayout(Robolectric.application);
    }

    @Test
    public void shouldStartActivityWhenSelectingItemInSinglePaneMode() {
        activity.onCreate(null);

        TrainingSession trainingSession = new TrainingSession();
        activity.onItemSelected(trainingSession);

        Intent startedIntent = Robolectric.shadowOf(activity).getNextStartedActivity();
        assertThat(startedIntent, is(notNullValue()));
        assertThat(startedIntent.getComponent().getClassName(),
                        equalTo(TrainingSessionDetailActivity.class.getCanonicalName()));
        assertThat(startedIntent.getParcelableExtra(TrainingSessionDetailFragment.ARG_ITEM_ID), equalTo((Parcelable)trainingSession));

        assertThat(activity.getSupportFragmentManager().findFragmentById(R.id.trainingsession_detail_container),
                        is(nullValue()));
    }

    @Test
    public void shouldReplaceFragmentWhenSelectingItemInMultiPaneMode() {
        activity.setTrainingSessionDetailContainer(detailContainer);
        activity.onCreate(null);

        TrainingSession trainingSession = new TrainingSession();
        activity.onItemSelected(trainingSession);

        Intent startedIntent = Robolectric.shadowOf(activity).getNextStartedActivity();
        assertThat(startedIntent, is(nullValue()));

        Fragment detailFragment = activity.getSupportFragmentManager().findFragmentById(
                        R.id.trainingsession_detail_container);
        assertThat(detailFragment, is(notNullValue()));
        assertThat(detailFragment, instanceOf(TrainingSessionDetailFragment.class));
        assertThat(detailFragment.getArguments(), is(notNullValue()));
        assertThat(detailFragment.getArguments().getParcelable(TrainingSessionDetailFragment.ARG_ITEM_ID), equalTo((Parcelable)trainingSession));
    }

}
