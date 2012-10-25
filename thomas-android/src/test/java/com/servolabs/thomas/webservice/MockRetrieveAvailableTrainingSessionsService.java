package com.servolabs.thomas.webservice;

import com.servolabs.thomas.domain.TrainingSession;

public class MockRetrieveAvailableTrainingSessionsService implements RetrieveAvailableTrainingSessionsService {

    public TrainingSession[] trainingSessions;

    @Override
    public TrainingSession[] retrieve() {
        return trainingSessions;
    }
}
