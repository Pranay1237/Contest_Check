package com.contest.app.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Contests")
public class ContestClass {

    @PrimaryKey
    @NonNull
    private String contestName;
    private String startTime;
    private String duration;
    private String timeLeft;
    private int image;
    private int daysLeft;

    public ContestClass(@NonNull String contestName, String startTime, int daysLeft, String timeLeft, String duration, int image) {
        this.contestName = contestName;
        this.startTime = startTime;
        this.daysLeft = daysLeft;
        this.duration = duration;
        this.timeLeft = timeLeft;
        this.image = image;
    }

    @NonNull
    public String getContestName() {
        return contestName;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public String getDuration() {
        return duration;
    }

    public int getImage() {
        return image;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setContestName(@NonNull String contestName) {
        this.contestName = contestName;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }
}
