package com.example.anapp;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LeetcodeContestScraper {

    public List<ContestClass> getContests() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://kontests.net/api/v1/leet_code";

        Request request = new Request.Builder().url(url).build();

        List<ContestClass> a = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()) {
                String responseBody = response.body().string();

                JSONArray jsonArray = new JSONArray(responseBody);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String name = jsonObject.getString("name");
                    String time = jsonObject.getString("start_time");
                    String duration = jsonObject.getString("duration");
                    int d = Integer.parseInt(duration)/3600;

                    time = convertTime(time);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
                    LocalDateTime currentDateTime = LocalDateTime.now();

                    int left = (int) ChronoUnit.DAYS.between(dateTime, currentDateTime);

                    a.add(new ContestClass(name, time, left, Integer.toString(d), R.drawable.leetcode));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public String convertTime(String time) {
        Instant instant = Instant.parse(time);

        ZoneId istZone = ZoneId.of("Asia/Kolkata");
        ZonedDateTime istTime = instant.atZone(istZone);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String istTimeString = istTime.format(formatter);

        return istTimeString;
    }
}
