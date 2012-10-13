package com.servolabs.thomas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.servolabs.robolectric.ThomasTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.tester.android.view.TestMenuItem;

@RunWith(ThomasTestRunner.class)
public class TrainingSessionDetailActivityTest {

    private static final String FRAGMENT_ID = "42";

    private TrainingSessionDetailActivity activity;

    @Before
    public void setUp() {
        activity = new TrainingSessionDetailActivity();

        Intent intent = new Intent();
        intent.putExtra(TrainingSessionDetailFragment.ARG_ITEM_ID, FRAGMENT_ID);
        activity.setIntent(intent);
    }

    @Test
    public void shouldAddFragmentBasedOnIntentWhenCreating() {
        activity.onCreate(null);

        Fragment detailFragment = activity.getSupportFragmentManager().findFragmentById(
                        R.id.trainingsession_detail_container);
        assertThat(detailFragment, is(notNullValue()));
        assertThat(detailFragment, instanceOf(TrainingSessionDetailFragment.class));
        assertThat(detailFragment.getArguments(), is(notNullValue()));
        assertThat(detailFragment.getArguments().getString(TrainingSessionDetailFragment.ARG_ITEM_ID),
                        equalTo(FRAGMENT_ID));
    }

    @Test
    public void shouldStartParentActivityAndCloseThisOneWhenHomeMenuItemSelected() {
        activity.onCreate(null);

        activity.onOptionsItemSelected(new TestMenuItem(android.R.id.home));

        Intent startedIntent = Robolectric.shadowOf(activity).getNextStartedActivity();
        assertThat(startedIntent, is(notNullValue()));
        assertThat(startedIntent.getComponent().getClassName(),
                        equalTo(TrainingSessionListActivity.class.getCanonicalName()));
        assertThat(startedIntent.getFlags(), equalTo(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        assertThat(activity.isFinishing(), is(true));
    }

}
