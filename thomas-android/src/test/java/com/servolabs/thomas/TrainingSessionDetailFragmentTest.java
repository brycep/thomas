package com.servolabs.thomas;

import android.app.Service;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.servolabs.robolectric.ThomasTestRunner;
import com.servolabs.thomas.domain.TrainingSession;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

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

        assertThat(fragment.trainingSession, is(nullValue()));
    }

    @Test
    public void shouldHaveCorrespondingItemIfFragmentCreatedWithItemArgument() {
        TrainingSession trainingSession = new TrainingSession();
        arguments.putParcelable(TrainingSessionDetailFragment.ARG_ITEM_ID, trainingSession);

        fragment.onCreate(null);

        assertThat(fragment.trainingSession, is(trainingSession));
    }

    @Test
    public void shouldSetDetailTextViewWithItemContentWhenCreatingView() throws Exception {
        TrainingSession trainingSession = new TrainingSession("UnitTest", "", new Date());
        arguments.putParcelable(TrainingSessionDetailFragment.ARG_ITEM_ID, trainingSession);
        fragment.onCreate(null);
        LayoutInflater inflater = (LayoutInflater) Robolectric.application
                        .getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        View rootView = fragment.onCreateView(inflater, null, null);

        assertThat(rootView, is(notNullValue()));
        TextView gameDetailView = (TextView) rootView.findViewById(R.id.trainingsession_detail);
        assertThat(gameDetailView, is(notNullValue()));
        assertThat(gameDetailView.getText().toString(), is(trainingSession.getCourseName()));
    }

}
