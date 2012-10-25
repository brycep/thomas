package com.servolabs.thomas.domain;

import java.util.Date;

public class TrainingSession {
    private String courseName;
    private String instructor;
    private Date startTime;

    public TrainingSession() {}

    public TrainingSession(String courseName, String instructor, Date startTime) {
        this.courseName = courseName;
        this.instructor = instructor;
        this.startTime = startTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public Date getStartTime() {
        return startTime;
    }

}
