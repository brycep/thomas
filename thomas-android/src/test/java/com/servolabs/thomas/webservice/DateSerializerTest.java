package com.servolabs.thomas.webservice;

import com.google.gson.JsonElement;
import com.servolabs.robolectric.ThomasTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(ThomasTestRunner.class)
public class DateSerializerTest {

    DateSerializer serializer = new DateSerializer();

    Date testTime = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

    @Before
    public void setUp() throws Exception  {
        testTime = simpleDateFormat.parse("2012-01-01T06:00:00+0000");
    }

    // I'm not sure if this test is going to work for developers in different time zones..
    // Maybe we should look at this one again in the future.
    @Test
    public void serializeDate() {
        JsonElement serializedDate = serializer.serialize(testTime, null, null);

        assertThat(serializedDate.getAsString(), is(equalTo("2012-01-01T00:00:00-0600")));
    }

    @Test
    public void serializerIsToughEnoughToHandleNullDates() throws Exception  {
        JsonElement serializedDate = serializer.serialize(null, null, null);

        assertThat(serializedDate, is(nullValue()));
    }

}
