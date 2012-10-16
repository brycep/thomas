package com.servolabs.thomas;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import com.servolabs.robolectric.ThomasTestRunner;

@RunWith(ThomasTestRunner.class)
public class TrainingSessionListFragmentTest {

    private TrainingSessionListFragment fragment;

    @Before
    public void setUpFragmentView() {
        fragment = new TrainingSessionListFragment();
        fragment.onCreateView(null, null, null);
    }

    @Test
    public void shouldSetAdapterWhenCreatingFragment() throws Exception {
        // TODO Set from DB (use ContentProvider/Loader?)
        fragment.onCreate(null);

        assertThat(fragment.getListAdapter(), is(notNullValue()));
        assertThat(fragment.getListAdapter().getCount(), is(3));
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
        fragment.onCreate(null); // Adds dummy content with 3 items

        fragment.setActivatedPosition(1);

        assertThat(fragment.getListView().isItemChecked(1), is(true));
    }

    @Test
    public void shouldUncheckItemWhenDeactivating() throws Exception {
        fragment.onCreate(null); // Adds dummy content with 3 items
        fragment.setActivatedPosition(1);

        fragment.setActivatedPosition(ListView.INVALID_POSITION);

        assertThat(fragment.getListView().isItemChecked(1), is(false));
    }

    @Test
    public void shouldNotSaveInstanceStateIfNoValidActivatedPosition() throws Exception {
        fragment.onCreate(null); // Adds dummy content with 3 items
        Bundle outState = new Bundle();

        fragment.onSaveInstanceState(outState);

        assertThat(outState.isEmpty(), is(true));
    }

    @Test
    public void shouldSaveInstanceStateWithValidActivatedPosition() throws Exception {
        fragment.onCreate(null); // Adds dummy content with 3 items
        fragment.setActivatedPosition(1);
        Bundle outState = new Bundle();

        fragment.onSaveInstanceState(outState);

        assertThat(outState.getInt(TrainingSessionListFragment.STATE_ACTIVATED_POSITION), is(1));
    }

    @Test
    public void shouldNotSetActivatedPositionWhenViewCreatedWithoutSavedInstanceState() throws Exception {
        fragment.onCreate(null); // Adds dummy content with 3 items

        fragment.onViewCreated(null, null);

        assertThat(fragment.getListView().getCheckedItemPosition(), is(ListView.INVALID_POSITION));
    }

    @Test
    public void shouldResetActivatedPositionWhenViewCreatedWithSavedInstanceState() throws Exception {
        fragment.onCreate(null); // Adds dummy content with 3 items
        Bundle savedInstanceState = new Bundle();
        savedInstanceState.putInt(TrainingSessionListFragment.STATE_ACTIVATED_POSITION, 2);

        fragment.onViewCreated(null, savedInstanceState);

        assertThat(fragment.getListView().isItemChecked(2), is(true));
    }

}
