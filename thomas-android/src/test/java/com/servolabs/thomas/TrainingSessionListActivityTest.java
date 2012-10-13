package com.servolabs.thomas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.servolabs.robolectric.ThomasTestRunner;
import com.xtremelabs.robolectric.Robolectric;

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

        activity.onItemSelected("42");

        Intent startedIntent = Robolectric.shadowOf(activity).getNextStartedActivity();
        assertThat(startedIntent, is(notNullValue()));
        assertThat(startedIntent.getComponent().getClassName(),
                        equalTo(TrainingSessionDetailActivity.class.getCanonicalName()));
        assertThat(startedIntent.getStringExtra(TrainingSessionDetailFragment.ARG_ITEM_ID), equalTo("42"));

        assertThat(activity.getSupportFragmentManager().findFragmentById(R.id.trainingsession_detail_container),
                        is(nullValue()));
    }

    @Test
    public void shouldReplaceFragmentWhenSelectingItemInMultiPaneMode() {
        activity.setTrainingSessionDetailContainer(detailContainer);
        activity.onCreate(null);

        activity.onItemSelected("42");

        Intent startedIntent = Robolectric.shadowOf(activity).getNextStartedActivity();
        assertThat(startedIntent, is(nullValue()));

        Fragment detailFragment = activity.getSupportFragmentManager().findFragmentById(
                        R.id.trainingsession_detail_container);
        assertThat(detailFragment, is(notNullValue()));
        assertThat(detailFragment, instanceOf(TrainingSessionDetailFragment.class));
        assertThat(detailFragment.getArguments(), is(notNullValue()));
        assertThat(detailFragment.getArguments().getString(TrainingSessionDetailFragment.ARG_ITEM_ID), equalTo("42"));
    }

}
