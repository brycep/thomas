package com.servolabs.thomas.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servolabs.thomas.domain.TrainingSession;
import org.springframework.http.*;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class RetrieveAvailableTrainingSessionsServiceImpl implements RetrieveAvailableTrainingSessionsService {

    private static final String URL = "http://thomas.servolabs.com/static/available-training-courses.json";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"); //2012-12-06T20:00:00+0000

    @Override
    public TrainingSession[] retrieve() {
        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create();

        converter.setGson(gson);
        restTemplate.getMessageConverters().add(converter);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        // Make the HTTP GET request, marshaling the response from JSON to an array of Events
        ResponseEntity<TrainingSession[]> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, TrainingSession[].class);
        return responseEntity.getBody();
    }

}
