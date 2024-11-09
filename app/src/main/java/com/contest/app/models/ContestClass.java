package com.contest.app.models;

public class ContestClass {

    String contestName, startTime, duration, timeLeft;
    int image, daysLeft;

    public ContestClass(String contestName, String startTime, int daysLeft, String timeLeft, String duration, int image) {
        this.contestName = contestName;
        this.startTime = startTime;
        this.daysLeft = daysLeft;
        this.duration = duration;
        this.timeLeft = timeLeft;
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

    public String getDuration() {
        return duration;
    }

    public int getImage() {
        return image;
    }

    public String getTimeLeft() {
        return timeLeft;
    }
}
