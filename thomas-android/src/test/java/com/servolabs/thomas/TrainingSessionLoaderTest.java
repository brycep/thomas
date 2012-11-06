package com.servolabs.thomas;

import com.google.common.collect.Lists;
import com.servolabs.robolectric.ShadowLoader;
import com.servolabs.robolectric.ThomasTestRunner;
import com.servolabs.thomas.domain.TrainingSession;
import com.servolabs.thomas.webservice.RetrieveAvailableTrainingSessionsService;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(ThomasTestRunner.class)
@SuppressWarnings("unchecked")
public class TrainingSessionLoaderTest {

    private RetrieveAvailableTrainingSessionsService service = Mockito.mock(RetrieveAvailableTrainingSessionsService.class);

    TrainingSession[] trainingSessions = new TrainingSession[1];

    private TrainingSessionListLoader listLoader = new TrainingSessionListLoader(Robolectric.application);

    @Before
    public void setUp()  {
        trainingSessions[0] = new TrainingSession();

        when(service.retrieve()).thenReturn(trainingSessions);
        listLoader.setService(service);
    }

    @Test
    public void loadFromTrainingSessionWebService()  {

        List<TrainingSession> results = listLoader.loadInBackground();

        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(equalTo(trainingSessions[0])));

    }

    // Right now we stick our fingers in our ears and shout "LA LA LA" when there's an exception
    // from the service.  We need to do something a little more friendly for the user, but we'll
    // get there.
    @Test
    public void returnEmptyListWhenServiceThrowsAnException()  {
        when(service.retrieve()).thenThrow(new RuntimeException("The webservice failed!"));

        List<TrainingSession> results = listLoader.loadInBackground();

        assertThat(results.size(), is(0));
    }

    @Test
    public void deliverResultsKeepsLastResultsFromService()  {
        listLoader.deliverResult(trainingSessionsAsList());

        ShadowLoader<List<TrainingSession>> shadowLoader
                = (ShadowLoader) Robolectric.shadowOf_(listLoader);
        assertThat(shadowLoader.deliveredResult.size(), is(1));
    }

    @Test
    public void abortClearsDataWhenLoaderIsReset()  {
        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(listLoader);
        shadowLoader.reset = true;

        listLoader.deliverResult(trainingSessionsAsList());

        // Assert that the deliveredResult was never delivered to the super() class.
        assertThat(shadowLoader.deliveredResult, is(nullValue()));
    }

    @Test
    public void clearOutLastTrainingSessionsIfNewestResultsAreEmpty()  {
        listLoader.deliverResult(trainingSessionsAsList());
        listLoader.deliverResult(Lists.<TrainingSession>newArrayList());

        assertThat(listLoader.getLastTrainingSessions().size(), is(0));
    }

    @Test
    public void onStartLoadingForcesLoaderToLoad()  {
        listLoader.onStartLoading();

        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(listLoader);
        assertThat(shadowLoader.forceLoaded, is(true));
    }

    @Test
    public void onStartLoadingReturnsCachedResultsIfWeHaveThem()  {
        listLoader.setLastTrainingSessions(trainingSessionsAsList());

        listLoader.onStartLoading();

        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(listLoader);
        assertThat(shadowLoader.deliveredResult, is(equalTo(trainingSessionsAsList())));
    }

    @Test
    public void onStopLoadingCallsCancelLoading()  {
        listLoader.onStopLoading();

        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(listLoader);
        assertThat(shadowLoader.cancelled, is(true));
    }

    @Test
    public void onCancelledClearsTrainingSessions()  {
        List<TrainingSession> trainingSessions = trainingSessionsAsList();
        listLoader.onCanceled(trainingSessions);

        assertThat(trainingSessions.isEmpty(), is(true));
    }

    @Test
    public void onResetStopsLoadingData()  {
        listLoader.onReset();

        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(listLoader);
        assertThat(shadowLoader.reset, is(true));
        assertThat(shadowLoader.cancelled, is(true));
    }

    @Test
    public void onResetClearsLastData()  {
        listLoader.setLastTrainingSessions(trainingSessionsAsList());
        listLoader.onReset();

        assertThat(listLoader.getLastTrainingSessions(), is(nullValue()));
    }

    private List<TrainingSession> trainingSessionsAsList()  {
        return Lists.newArrayList(this.trainingSessions);
    }

}
