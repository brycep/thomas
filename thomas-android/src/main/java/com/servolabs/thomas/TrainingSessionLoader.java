package com.servolabs.thomas;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.servolabs.thomas.domain.TrainingSession;
import com.servolabs.thomas.webservice.RetrieveAvailableTrainingSessionsService;
import com.servolabs.thomas.webservice.RetrieveAvailableTrainingSessionsServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Lifecycle methods based on the AbstractCursorLoader class in cwac-loaderex.
 */
public class TrainingSessionLoader extends AsyncTaskLoader<List<TrainingSession>> {

    private static final String LOG_TAG = TrainingSessionLoader.class.getSimpleName();

    private RetrieveAvailableTrainingSessionsService service;
    private List<TrainingSession> lastTrainingSessions;

    public TrainingSessionLoader(Context context) {
        super(context);
    }

    @Override
    public List<TrainingSession> loadInBackground() {
        Log.d(LOG_TAG, "Calling service to load training sessions");
        lastTrainingSessions = new ArrayList<TrainingSession>();
        try {
            TrainingSession[] sessionResults = getService().retrieve();
            Log.d(LOG_TAG, "Preparing service results for delivery");
            for (TrainingSession session : sessionResults) {
                lastTrainingSessions.add(session);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Unable to load training sessions: " + e.getMessage());
        }

        return lastTrainingSessions;
    }

    /**
     * Runs on the UI thread, routing the results from the background thread to whatever is using the list (e.g. an
     * ArrayAdapter).
     */
    @Override
    public void deliverResult(List<TrainingSession> trainingSessions) {
        Log.d(LOG_TAG, "In deliverResult with argument = " + trainingSessions);
        if (isReset()) {
            // An async query came in while the loader is stopped
            if (trainingSessions != null) {
                trainingSessions.clear();
            }

            return;
        }

        List<TrainingSession> oldTrainingSessions = lastTrainingSessions;
        lastTrainingSessions = trainingSessions;

        if (isStarted()) {
            super.deliverResult(trainingSessions);
        }

        if (oldTrainingSessions != null && oldTrainingSessions != trainingSessions && !oldTrainingSessions.isEmpty()) {
            oldTrainingSessions.clear();
        }
    }

    /**
     * Starts an asynchronous load of the list deliveredResult. When the result is ready the callbacks will be called on the UI
     * thread. If a previous load has been completed and is still valid the result may be passed to the callbacks
     * immediately. Must be called from the UI thread.
     */
    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG, "In onStartLoading");
        if (lastTrainingSessions != null) {
            deliverResult(lastTrainingSessions);
        }
        if (takeContentChanged() || lastTrainingSessions == null) {
            forceLoad();
        }
    }

    /**
     * Must be called from the UI thread, triggered by a call to stopLoading().
     */
    @Override
    protected void onStopLoading() {
        Log.d(LOG_TAG, "In onStopLoading");
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Must be called from the UI thread, triggered by a call to cancel(). Here, we make sure our Cursor is closed, if
     * it still exists and is not already closed.
     */
    @Override
    public void onCanceled(List<TrainingSession> trainingSessions) {
        Log.d(LOG_TAG, "In onCanceled with argument = " + trainingSessions);
        if (trainingSessions != null && !trainingSessions.isEmpty()) {
            trainingSessions.clear();
        }
    }

    /**
     * Must be called from the UI thread, triggered by a call to reset(). Here, we make sure our Cursor is closed, if it
     * still exists and is not already closed.
     */
    @Override
    protected void onReset() {
        Log.d(LOG_TAG, "In onReset");
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        if (lastTrainingSessions != null && !lastTrainingSessions.isEmpty()) {
            lastTrainingSessions.clear();
        }

        lastTrainingSessions = null;
    }

    RetrieveAvailableTrainingSessionsService getService() {
        return service == null ? new RetrieveAvailableTrainingSessionsServiceImpl() : service;
    }

    /**
     * Added to support unit test
     */
    void setService(RetrieveAvailableTrainingSessionsService service) {
        this.service = service;
    }

    /**
     * Also added to support unit tests
     */
    List<TrainingSession> getLastTrainingSessions() {
        return lastTrainingSessions;
    }

    void setLastTrainingSessions(List<TrainingSession> trainingSessions)  {
        this.lastTrainingSessions = trainingSessions;
    }
}
