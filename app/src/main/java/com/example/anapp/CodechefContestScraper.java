package com.example.anapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CodechefContestScraper {

    public List<ContestClass> getContests() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://kontests.net/api/v1/code_chef";

        Request request = new Request.Builder().url(url).build();

        List<ContestClass> a = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()) {
                String responseBody = response.body().string();

                JSONArray jsonArray = new JSONArray(responseBody);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String dateTime = jsonObject.getString("start_time");
                    String date = dateTime.substring(0, 10);
                    String name = jsonObject.getString("name");
                    String d = jsonObject.getString("duration");

                    dateTime = dateTime.replace(" ", "T");
                    dateTime = dateTime.replace("TUTC", "Z");

                    String time = convertTime(dateTime);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm");

                    LocalDateTime currentDateTime = LocalDateTime.now();
                    LocalDateTime givenDateTime = LocalDateTime.parse(time, formatter);

                    Duration duration = Duration.between(currentDateTime, givenDateTime);

                    int left = (int)duration.toDays();

                    if(left >= 0) {

                        String hrs = Integer.toString(Integer.parseInt(d)/3600);
                        String min = Integer.toString(Integer.parseInt(d)%60);

                        if(hrs.length() == 1) {
                            hrs = "0" + hrs;
                        }
                        if(min.length() == 1) {
                            min = "0" + min;
                        }

                        String dur = hrs + ":" + min;

                        DateTimeFormatter f = DateTimeFormatter.ofPattern("E");

                        String day = givenDateTime.format(f);

                        a.add(new ContestClass(name, time, day, left, dur, R.drawable.codechef));
                    }
                }
            } else {
                System.out.println("Response was not Successful. Response code : " + response.code());
            }
            return a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public String convertTime(String time) {
        Instant instant = Instant.parse(time);

        ZoneId istZone = ZoneId.of("Asia/Kolkata");
        ZonedDateTime istTime = instant.atZone(istZone);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm");
        String istTimeString = istTime.format(formatter);

        return istTimeString;
    }
}
