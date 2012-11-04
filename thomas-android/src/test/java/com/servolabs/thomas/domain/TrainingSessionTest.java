package com.servolabs.thomas.domain;

import com.servolabs.robolectric.ThomasTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ThomasTestRunner.class)
public class TrainingSessionTest {
    Date now = new Date();
    TrainingSession trainingSession = new TrainingSession("TestCourseName", "TestInstructor", now);

    @Test
    public void initializingConstructor()  {

        assertThat(trainingSession.getCourseName(), is(equalTo("TestCourseName")));
        assertThat(trainingSession.getInstructor(), is(equalTo("TestInstructor")));
        assertThat(trainingSession.getStartTime(), is(now));
    }

    @Test
    public void toStringOutputsNameAndInstructor()  {
        assertThat(trainingSession.toString(), is(equalTo("TestCourseName - TestInstructor")));
    }
}
