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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
