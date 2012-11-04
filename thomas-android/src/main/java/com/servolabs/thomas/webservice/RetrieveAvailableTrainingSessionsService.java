package com.servolabs.thomas.webservice;

import com.servolabs.thomas.domain.TrainingSession;

public interface RetrieveAvailableTrainingSessionsService {

    static String DATE_TEXT_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"; //2012-12-06T20:00:00+0000

    TrainingSession[] retrieve();
}
