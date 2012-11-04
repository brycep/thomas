package com.servolabs.thomas.webservice;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer implements JsonDeserializer<Date> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(RetrieveAvailableTrainingSessionsService.DATE_TEXT_FORMAT);

    @Override
    public Date deserialize(JsonElement jsonElement,
                            Type type,
                            JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        Date result = null;
        try  {
            result = jsonElement == null ? null : dateFormat.parse(jsonElement.getAsString());
        } catch(ParseException exp)  {
            // Log that we had trouble parsing a date.
        }
        return result;
    }
}

