package com.servolabs.thomas;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.servolabs.robolectric.ThomasTestRunner;
import com.servolabs.thomas.domain.TrainingSession;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.tester.android.util.TestLoaderManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(ThomasTestRunner.class)
public class TrainingSessionListFragmentTest {

    private TrainingSessionListFragment fragment;

    private Loader<List<TrainingSession>> trainingSessionListLoader;

    @Before
    public void setUpFragmentView() {
        fragment = new TrainingSessionListFragment();

        trainingSessionListLoader = new TestLoader(Robolectric.application);
        fragment.setTrainingSessionListLoader(trainingSessionListLoader);

        fragment.onCreate(null);
        fragment.onCreateView(null, null, null);
        fragment.onActivityCreated(null);
    }

    @Test
    public void shouldSetAdapterWhenCreatingFragment() throws Exception {
        TestLoaderManager loaderManager = (TestLoaderManager) Robolectric.shadowOf(fragment).getLoaderManager();
        Loader<List<TrainingSession>> loader = loaderManager.getLoader(TrainingSessionListFragment.LIST_LOADER_ID);
        assertThat(loader, is(trainingSessionListLoader));

        assertThat(fragment.getListAdapter(), is(notNullValue()));
        assertThat(fragment.getListAdapter().getCount(), is(0));
    }

    @Test
    public void shouldSwapInNewListWhenLoaderFinished() throws Exception {
        List<TrainingSession> trainingSessions = Arrays.asList(new TrainingSession(), new TrainingSession());

        fragment.onLoadFinished(null, trainingSessions);

        assertThat(fragment.getListAdapter().getCount(), is(trainingSessions.size()));
    }

    @Test
    public void shouldEmptyOutListWhenLoaderReset() throws Exception {
        fragment.onLoadFinished(null, Arrays.asList(new TrainingSession(), new TrainingSession()));

        fragment.onLoaderReset(null);

        assertThat(fragment.getListAdapter().getCount(), is(0));
    }

    // TODO onListItemClick invokes callback - change to Otto!

    @Test
    public void shouldSetListViewChoiceModeToNoneIfActivateOnItemClickFalse() throws Exception {
        fragment.setActivateOnItemClick(false);

        assertThat(fragment.getListView().getChoiceMode(), is(AbsListView.CHOICE_MODE_NONE));
    }

    @Test
    public void shouldSetListViewChoiceModeToSingleIfActivateOnItemClickTrue() throws Exception {
        fragment.setActivateOnItemClick(true);

        assertThat(fragment.getListView().getChoiceMode(), is(AbsListView.CHOICE_MODE_SINGLE));
    }

    @Test
    public void shouldCheckItemWhenActivating() throws Exception {
        fragment.setActivatedPosition(1);

        assertThat(fragment.getListView().isItemChecked(1), is(true));
    }

    @Test
    public void shouldUncheckItemWhenDeactivating() throws Exception {
        fragment.setActivatedPosition(1);

        fragment.setActivatedPosition(ListView.INVALID_POSITION);

        assertThat(fragment.getListView().isItemChecked(1), is(false));
    }

    @Test
    public void shouldNotSaveInstanceStateIfNoValidActivatedPosition() throws Exception {
        Bundle outState = new Bundle();

        fragment.onSaveInstanceState(outState);

        assertThat(outState.isEmpty(), is(true));
    }

    @Test
    public void shouldSaveInstanceStateWithValidActivatedPosition() throws Exception {
        fragment.setActivatedPosition(1);
        Bundle outState = new Bundle();

        fragment.onSaveInstanceState(outState);

        assertThat(outState.getInt(TrainingSessionListFragment.STATE_ACTIVATED_POSITION), is(1));
    }

    @Test
    public void shouldNotSetActivatedPositionWhenViewCreatedWithoutSavedInstanceState() throws Exception {
        fragment.onViewCreated(null, null);

        assertThat(fragment.getListView().getCheckedItemPosition(), is(ListView.INVALID_POSITION));
    }

    @Test
    public void shouldResetActivatedPositionWhenViewCreatedWithSavedInstanceState() throws Exception {
        Bundle savedInstanceState = new Bundle();
        savedInstanceState.putInt(TrainingSessionListFragment.STATE_ACTIVATED_POSITION, 2);

        fragment.onViewCreated(null, savedInstanceState);

        assertThat(fragment.getListView().isItemChecked(2), is(true));
    }

    @Test
    public void nullCallbackImplementationShouldDoNothing()  throws Exception  {
        Throwable somethingBad = null;
        try  {
            fragment.sNullCallbacks.onItemSelected(new TrainingSession());
        } catch(Throwable exp)  {
            somethingBad = exp;
        }

        // Make sure nothing bad happened.
        assertThat(somethingBad, is(nullValue()));
    }

    @Test
    public void onDetatchShouldPutNullCallbacksInPlace()  {
        fragment.mCallbacks = new TrainingSessionListFragment.Callbacks() {
            @Override
            public void onItemSelected(TrainingSession trainingSession) {
                fail("This callback shouldn't be active after detaching from the activity");
            }
        };

        fragment.onDetach();

        // Since the 'null' callbacks should have been reattached to the
        // fragment after the onDetach() was called.
        fragment.mCallbacks.onItemSelected(new TrainingSession());
    }

    @Test
    public void onListItemClickDeliversSelectedObjectToCallback()  {
        TrainingSessionListFragment.Callbacks callback = Mockito.mock(TrainingSessionListFragment.Callbacks.class);
        ListAdapter listAdapter = Mockito.mock(ListAdapter.class);
        ListView listView = new ListView(Robolectric.application.getBaseContext());
        View view = new View(Robolectric.application.getBaseContext());

        fragment.mCallbacks = callback;
        fragment.setListAdapter(listAdapter);

        TrainingSession trainingSession = new TrainingSession();

        when(listAdapter.getItem(1)).thenReturn(trainingSession);

        fragment.onListItemClick(listView, view, 1, 0);

        verify(callback).onItemSelected(trainingSession);
    }

    private class TestLoader extends Loader<List<TrainingSession>> {

        public TestLoader(Context context) {
            super(context);
        }
    }

}
