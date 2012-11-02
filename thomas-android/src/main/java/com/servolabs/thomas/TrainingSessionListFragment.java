package com.servolabs.thomas;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.servolabs.thomas.domain.TrainingSession;
import com.servolabs.thomas.dummy.DummyContent;

public class TrainingSessionListFragment extends ListFragment implements LoaderCallbacks<List<TrainingSession>> {

    static final String STATE_ACTIVATED_POSITION = "activated_position";

    static final int LIST_LOADER_ID = 1;

    private static final String LOG_TAG = TrainingSessionListFragment.class.getSimpleName();

    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;

    private Loader<List<TrainingSession>> trainingSessionListLoader; // TODO Replace with factory (a la Farpost2)

    public interface Callbacks {

        public void onItemSelected(String id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    public TrainingSessionListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
         * This will cause unit tests to fail because cannot access resources without attaching an Activity and
         * Robolectric's ShadowListFragment does not implement Callbacks!
         */
        // setEmptyText(getResources().getString(R.string.empty_training_session_list));

        getLoaderManager().initLoader(LIST_LOADER_ID, null, this);
        setListAdapter(createListAdapter(new ArrayList<TrainingSession>()));
    }

    private ArrayAdapter<TrainingSession> createListAdapter(List<TrainingSession> trainingSessions) {
        // TODO Replace with custom adapter based on custom layout for training session.
        return new ArrayAdapter<TrainingSession>(getActivity(), android.R.layout.simple_list_item_activated_1,
                        android.R.id.text1, trainingSessions);
    }

    @Override
    public Loader<List<TrainingSession>> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "In onCreateLoader");
        return trainingSessionListLoader == null ? new TrainingSessionLoader(getActivity().getApplicationContext())
                        : trainingSessionListLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<TrainingSession>> loader, List<TrainingSession> data) {
        // TODO Will this actually work? Do we need to manipulate the existing adapter instead?
        Log.d(LOG_TAG, "In onLoadFinished with this data: " + data);
        setListAdapter(createListAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<List<TrainingSession>> loader) {
        setListAdapter(createListAdapter(new ArrayList<TrainingSession>()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    /**
     * Added to support unit test
     */
    void setTrainingSessionListLoader(Loader<List<TrainingSession>> trainingSessionListLoader) {
        this.trainingSessionListLoader = trainingSessionListLoader;
    }
}
