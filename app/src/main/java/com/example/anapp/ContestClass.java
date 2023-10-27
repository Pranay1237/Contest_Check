package com.example.anapp;

public class ContestClass {

    String contestName, startTime, Duration;
    int image;

    public ContestClass(String contestName, String startTime, String Duration, int image) {
        this.contestName = contestName;
        this.startTime = startTime;
        this.Duration = Duration;
        this.image = image;
    }

    public String getContestName() {
        return contestName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getDuration() {
        return Duration + " hrs";
    }

    public int getImage() {
        return image;
    }
}
