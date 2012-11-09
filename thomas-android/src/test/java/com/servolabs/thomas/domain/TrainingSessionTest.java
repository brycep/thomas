package com.servolabs.thomas.domain;

import android.os.Parcel;
import com.servolabs.robolectric.ThomasTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void describeContentsReturnsZero()  {
        assertThat(trainingSession.describeContents(), is(0));
    }

    @Test
    public void writeToParcel()  {
        Parcel parcel = Mockito.mock(Parcel.class);
        trainingSession.writeToParcel(parcel, 0);

        verify(parcel).writeString("TestCourseName");
        verify(parcel).writeString("TestInstructor");
        verify(parcel).writeLong(now.getTime());
    }

    @Test
    public void writeToParcelHandlesNulls()  {
        TrainingSession emptyTrainingSession = new TrainingSession();
        Parcel parcel = Mockito.mock(Parcel.class);

        emptyTrainingSession.writeToParcel(parcel, 0);

        verify(parcel, times(2)).writeString(null);
        verify(parcel).writeLong(0);
    }

    @Test
    public void creatorCanConstructTrainingSession()  {
        Parcel parcel = Mockito.mock(Parcel.class);
        when(parcel.readString()).thenReturn("TestCourseName", "TestInstructor");
        when(parcel.readLong()).thenReturn(now.getTime());

        TrainingSession newTrainingSession = TrainingSession.CREATOR.createFromParcel(parcel);

        assertThat(newTrainingSession.getCourseName(), is(equalTo("TestCourseName")));
        assertThat(newTrainingSession.getInstructor(), is(equalTo("TestInstructor")));
        assertThat(newTrainingSession.getStartTime(), is(equalTo(now)));
    }

    @Test
    public void creatorCanConstructTrainingSessionFromNull()  {
        Parcel parcel = Mockito.mock(Parcel.class);
        when(parcel.readString()).thenReturn(null, null);
        when(parcel.readLong()).thenReturn(0L);

        TrainingSession newTrainingSession = TrainingSession.CREATOR.createFromParcel(parcel);

        assertThat(newTrainingSession.getCourseName(), is(nullValue()));
        assertThat(newTrainingSession.getInstructor(), is(nullValue()));
        assertThat(newTrainingSession.getStartTime(), is(nullValue()));
    }

    @Test
    public void createArrayOfTrainingSessions()  {
        TrainingSession[] trainingSessions = TrainingSession.CREATOR.newArray(1);
        assertThat(trainingSessions.length, is(1));
    }
}
