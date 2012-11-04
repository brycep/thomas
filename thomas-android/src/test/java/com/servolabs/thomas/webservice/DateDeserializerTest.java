package com.servolabs.thomas.webservice;

import com.google.gson.JsonElement;
import com.servolabs.robolectric.ThomasTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(ThomasTestRunner.class)
public class DateDeserializerTest {

    DateDeserializer deserializer = new DateDeserializer();

    JsonElement jsonElement = Mockito.mock(JsonElement.class);

    @Test
    public void deserializeDate() throws Exception  {

        String testTime = "2012-01-01T06:00:00+0000";

        when(jsonElement.getAsString()).thenReturn(testTime);

        Date deserializedDate = deserializer.deserialize(jsonElement, null, null);

        Date testDateTime = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(testTime);
        assertThat(deserializedDate, is(equalTo(testDateTime)));
    }

    @Test
    public void unparsableDateIsUnparsable() throws Exception  {
        when(jsonElement.getAsString()).thenReturn("Not a valid date");

        Date deserializedDate = deserializer.deserialize(jsonElement, null, null);

        assertThat(deserializedDate, is(nullValue()));
    }

    @Test
    public void dateDeserializerIsToughEnoughToHandleNull() throws Exception  {
        Date deserializedDate = deserializer.deserialize(null, null, null);
        assertThat(deserializedDate, is(nullValue()));
    }


}
