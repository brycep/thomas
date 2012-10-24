package com.servolabs.thomas.webservice;

import com.servolabs.thomas.domain.TrainingSession;
import org.springframework.http.*;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class RetrieveAvailableTrainingSessionsServiceImpl implements RetrieveAvailableTrainingSessionsService {

    private static final String URL = "http://thomas.servolabs.com/static/available-training-courses.json";

    @Override
    public TrainingSession[] retrieve() {
        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

//        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        // Make the HTTP GET request, marshaling the response from JSON to an array of Events
        ResponseEntity<TrainingSession[]> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, TrainingSession[].class);
        return responseEntity.getBody();
    }

}
