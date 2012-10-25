package com.servolabs.thomas;

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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(ThomasTestRunner.class)
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
}
