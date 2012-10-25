package com.servolabs.thomas;

import com.servolabs.robolectric.ThomasTestRunner;
import com.servolabs.thomas.domain.TrainingSession;
import com.servolabs.thomas.webservice.MockRetrieveAvailableTrainingSessionsService;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ThomasTestRunner.class)
public class TrainingSessionLoaderTest {

    private MockRetrieveAvailableTrainingSessionsService service = new MockRetrieveAvailableTrainingSessionsService();

    private TrainingSessionLoader loader = new TrainingSessionLoader(Robolectric.application);

    @Before
    public void setUp()  {
        service.trainingSessions = new TrainingSession[1];
        service.trainingSessions[0] = new TrainingSession();

        loader.setService(service);
    }

    @Test
    public void loadFromTrainingSessionWebService()  {

        List<TrainingSession> results = loader.loadInBackground();

        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(equalTo(service.trainingSessions[0])));

    }
}
