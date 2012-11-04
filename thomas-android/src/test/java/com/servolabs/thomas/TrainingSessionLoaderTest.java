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
        assertThat(shadowLoader.data.size(), is(1));
    }

    @Test
    public void abortClearsDataWhenLoaderIsReset()  {
        ShadowLoader<List<TrainingSession>> shadowLoader = (ShadowLoader) Robolectric.shadowOf_(loader);
        shadowLoader.reset = true;

        loader.deliverResult(trainingSessionsAsList());

        // Assert that the data was never delivered to the super() class.
        assertThat(shadowLoader.data, is(nullValue()));
    }

    @Test
    public void clearOutLastTrainingSessionsIfNewestResultsAreEmpty()  {
        loader.deliverResult(trainingSessionsAsList());
        loader.deliverResult(Lists.<TrainingSession>newArrayList());

        assertThat(loader.getLastTrainingSessions().size(), is(0));
    }

    private List<TrainingSession> trainingSessionsAsList()  {
        return Lists.newArrayList(this.trainingSessions);
    }

}
