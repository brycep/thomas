package com.servolabs.thomas.webservice;

import com.servolabs.thomas.domain.TrainingSession;

public interface RetrieveAvailableTrainingSessionsService {
    TrainingSession[] retrieve();
}
