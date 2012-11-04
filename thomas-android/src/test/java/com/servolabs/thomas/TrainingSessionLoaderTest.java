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

    private TrainingSessionLoader loader = new TrainingSessionLoader(Robolectric.application);

    @Before
    public void setUp()  {
        trainingSessions[0] = new TrainingSession();

        when(service.retrieve()).thenReturn(trainingSessions);
        loader.setService(service);
    }

    @Test
    public void loadFromTrainingSessionWebService()  {

        List<TrainingSession> results = loader.loadInBackground();

        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(equalTo(trainingSessions[0])));

    }

    // Right now we stick our fingers in our ears and shout "LA LA LA" when there's an exception
    // from the service.  We need to do something a little more friendly for the user, but we'll
    // get there.
    @Test
    public void returnEmptyListWhenServiceThrowsAnException()  {
        when(service.retrieve()).thenThrow(new RuntimeException("The webservice failed!"));

        List<TrainingSession> results = loader.loadInBackground();

        assertThat(results.size(), is(0));
    }

    @Test
    public void deliverResultsKeepsLastResultsFromService()  {
        loader.deliverResult(trainingSessionsAsList());

        ShadowLoader<List<TrainingSession>> shadowLoader
                = (ShadowLoader) Robolectric.shadowOf_(loader);
        assertThat(shadowLoader.deliveredResult.size(), is(1));
    }

    @Test
    public void abortClearsDataWhenLoaderIsReset()  {
        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(loader);
        shadowLoader.reset = true;

        loader.deliverResult(trainingSessionsAsList());

        // Assert that the deliveredResult was never delivered to the super() class.
        assertThat(shadowLoader.deliveredResult, is(nullValue()));
    }

    @Test
    public void clearOutLastTrainingSessionsIfNewestResultsAreEmpty()  {
        loader.deliverResult(trainingSessionsAsList());
        loader.deliverResult(Lists.<TrainingSession>newArrayList());

        assertThat(loader.getLastTrainingSessions().size(), is(0));
    }

    @Test
    public void onStartLoadingForcesLoaderToLoad()  {
        loader.onStartLoading();

        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(loader);
        assertThat(shadowLoader.forceLoaded, is(true));
    }

    @Test
    public void onStartLoadingReturnsCachedResultsIfWeHaveThem()  {
        loader.setLastTrainingSessions(trainingSessionsAsList());

        loader.onStartLoading();

        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(loader);
        assertThat(shadowLoader.deliveredResult, is(equalTo(trainingSessionsAsList())));
    }

    @Test
    public void onStopLoadingCallsCancelLoading()  {
        loader.onStopLoading();

        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(loader);
        assertThat(shadowLoader.cancelled, is(true));
    }

    @Test
    public void onCancelledClearsTrainingSessions()  {
        List<TrainingSession> trainingSessions = trainingSessionsAsList();
        loader.onCanceled(trainingSessions);

        assertThat(trainingSessions.isEmpty(), is(true));
    }

    @Test
    public void onResetStopsLoadingData()  {
        loader.onReset();

        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(loader);
        assertThat(shadowLoader.reset, is(true));
        assertThat(shadowLoader.cancelled, is(true));
    }

    @Test
    public void onResetClearsLastData()  {
        loader.setLastTrainingSessions(trainingSessionsAsList());
        loader.onReset();

        assertThat(loader.getLastTrainingSessions(), is(nullValue()));
    }

    private List<TrainingSession> trainingSessionsAsList()  {
        return Lists.newArrayList(this.trainingSessions);
    }

}
