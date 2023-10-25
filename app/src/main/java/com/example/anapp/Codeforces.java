package com.example.anapp;

public class Codeforces {

    String contestName, startTime, length;
    int image;

    public Codeforces(String contestName, String startTime, String length, int image) {
        this.contestName = contestName;
        this.startTime = startTime;
        this.length = length;
        this.image = image;
    }

    public String getContestName() {
        return contestName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getLength() {
        return length;
    }

    public int getImage() {
        return image;
    }
}
