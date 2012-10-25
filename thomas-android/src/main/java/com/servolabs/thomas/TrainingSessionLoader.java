package com.servolabs.thomas;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.servolabs.thomas.domain.TrainingSession;
import com.servolabs.thomas.webservice.RetrieveAvailableTrainingSessionsService;

import java.util.ArrayList;
import java.util.List;

public class TrainingSessionLoader extends AsyncTaskLoader<List<TrainingSession>> {

    private RetrieveAvailableTrainingSessionsService service;

    public TrainingSessionLoader(Context context) {
        super(context);
    }

    @Override
    public List<TrainingSession> loadInBackground() {
        TrainingSession[] sessionResults = service.retrieve();

        List<TrainingSession> sessions = new ArrayList<TrainingSession>();
        for(TrainingSession session : sessionResults)  {
            sessions.add(session);
        }
        return sessions;
    }

    public void setService(RetrieveAvailableTrainingSessionsService service) {
        this.service = service;
    }
}
