package com.servolabs.thomas;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Service;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.servolabs.robolectric.ThomasTestRunner;
import com.servolabs.thomas.dummy.DummyContent;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(ThomasTestRunner.class)
public class TrainingSessionDetailFragmentTest {

    private TrainingSessionDetailFragment fragment;
    private Bundle arguments;

    @Before
    public void setUpFragment() {
        fragment = new TrainingSessionDetailFragment();
        arguments = new Bundle();
        fragment.setArguments(arguments);
    }

    @Test
    public void shouldNotHaveAnItemIfFragmentCreatedWithoutItemArgument() {
        fragment.onCreate(null);

        assertThat(fragment.mItem, is(nullValue()));
    }

    // TODO return item from DB
    @Test
    public void shouldHaveCorrespondingItemIfFragmentCreatedWithItemArgument() {
        arguments.putString(TrainingSessionDetailFragment.ARG_ITEM_ID, "2");

        fragment.onCreate(null);

        assertThat(fragment.mItem, is(DummyContent.ITEM_MAP.get("2")));
    }

    // TODO Replace with training session-specific view population
    @Test
    public void shouldSetDetailTextViewWithItemContentWhenCreatingView() throws Exception {
        arguments.putString(TrainingSessionDetailFragment.ARG_ITEM_ID, "2");
        fragment.onCreate(null);
        LayoutInflater inflater = (LayoutInflater) Robolectric.application
                        .getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        View rootView = fragment.onCreateView(inflater, null, null);

        assertThat(rootView, is(notNullValue()));
        TextView gameDetailView = (TextView) rootView.findViewById(R.id.trainingsession_detail);
        assertThat(gameDetailView, is(notNullValue()));
        assertThat(gameDetailView.getText().toString(), is(DummyContent.ITEM_MAP.get("2").content));
    }

}
