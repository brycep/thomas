package com.servolabs.thomas.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class TrainingSession implements Parcelable  {
    private String courseName;
    private String instructor;
    private Date startTime;
    private int lengthInMinutes;

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

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    @Override
    public String toString() {
        return courseName + " - " + instructor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseName);
        dest.writeString(instructor);
        if (null == startTime)  {
            dest.writeLong(0);
        } else  {
            dest.writeLong(startTime.getTime());
        }
    }

    public static final Parcelable.Creator<TrainingSession> CREATOR
            = new Parcelable.Creator<TrainingSession>() {
        public TrainingSession createFromParcel(Parcel in) {
            return new TrainingSession(in);
        }

        public TrainingSession[] newArray(int size) {
            return new TrainingSession[size];
        }
    };

    private TrainingSession(Parcel in) {
        courseName = in.readString();
        instructor = in.readString();

        long startTimeValue = in.readLong();
        if (0 != startTimeValue)  {
            startTime = new Date(in.readLong());
        }
    }
}
