package com.servolabs.thomas.webservice;

import com.servolabs.robolectric.ThomasTestRunner;
import com.servolabs.thomas.domain.TrainingSession;
import com.xtremelabs.robolectric.Robolectric;
import org.apache.http.message.BasicHeader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(ThomasTestRunner.class)
public class RetrieveAvailableTrainingSessionsServiceTest {
    private static final String SAMPLE_TRAINING_SESSIONS_JSON_RESPONSE =
            "["+
                    "    {\n"+
                    "       \"courseName\":\"Introduction to Git\",\n"+
                    "       \"instructor\":\"James Seville\",\n"+
                    "       \"startTime\":\"Dec 01, 2012 1:00:00 PM\"\n"+
                    "    },\n"+
                    "    {\n"+
                    "        \"courseName\":\"Maven Tips and Tricks\",\n"+
                    "        \"instructor\":\"Henry Rotterdam\",\n"+
                    "        \"startTime\":\"Dec 04, 2012 2:00:00 PM\"\n" +
                    "    },\n"+
                    "    {\n"+
                    "        \"courseName\":\"Advanced StringBuffer Techniques\",\n"+
                    "        \"instructor\":\"Bert Liverpool\",\n"+
                    "        \"startTime\":\"Dec 06, 2012 5:00:00 PM\"\n"+
                    "    } ]";

    RetrieveAvailableTrainingSessionsService service = new RetrieveAvailableTrainingSessionsServiceImpl();

    @Before
    public void setUp()  {
        Robolectric.setDefaultHttpResponse(200, "OK");

    }

    @Test
    public void retrieveSessions()  {
        Robolectric.addPendingHttpResponse(200, SAMPLE_TRAINING_SESSIONS_JSON_RESPONSE, new BasicHeader("Content-Type", "application/json"));

        TrainingSession[] sessions = service.retrieve();

        assertThat(sessions, is(notNullValue()));
        assertThat(sessions.length, is(3));
    }

    @Test
    public void expectedDataIsReturned() throws Exception  {
        Robolectric.addPendingHttpResponse(200, SAMPLE_TRAINING_SESSIONS_JSON_RESPONSE, new BasicHeader("Content-Type", "application/json"));

        TrainingSession[] sessions = service.retrieve();

        assertThat(sessions[0].getCourseName(), is(equalTo("Introduction to Git")));
        assertThat(sessions[0].getInstructor(), is(equalTo("James Seville")));
        assertThat(sessions[0].getStartTime(), is(equalTo(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").parse("12/01/2012 13:00:00"))));
    }

}
