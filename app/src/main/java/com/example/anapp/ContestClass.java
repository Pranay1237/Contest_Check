package com.example.anapp;

public class ContestClass {

    String contestName, startTime, day, duration;
    int image, daysLeft;

    public ContestClass(String contestName, String startTime, String day, int daysLeft, String duration, int image) {
        this.contestName = contestName;
        this.startTime = startTime;
        this.day = day;
        this.daysLeft = daysLeft;
        this.duration = duration;
        this.image = image;
    }

    public String getContestName() {
        return contestName;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public String getDay() {
        return day;
    }

    public String getDuration() {
        return duration;
    }

    public int getImage() {
        return image;
    }
}
