package com.servolabs.thomas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.servolabs.thomas.domain.TrainingSession;

public class TrainingSessionDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    TrainingSession trainingSession;

    public TrainingSessionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            trainingSession = (TrainingSession) getArguments().getParcelable(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trainingsession_detail, container, false);
        if (trainingSession != null) {
            ((TextView) rootView.findViewById(R.id.trainingsession_detail)).setText(trainingSession.getCourseName());
        }
        return rootView;
    }
}
