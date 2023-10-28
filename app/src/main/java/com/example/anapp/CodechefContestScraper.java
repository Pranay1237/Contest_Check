package com.example.anapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
                    String duration = jsonObject.getString("duration");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    LocalDate localDate = LocalDate.parse(date, formatter);
                    LocalDate currentDate = LocalDate.now();

                    if(localDate.isAfter(currentDate)) {
                        int d = Integer.parseInt(duration)/3600;
                        dateTime = dateTime.replace(" ", "T");
                        dateTime = dateTime.replace("TUTC", "Z");

                        String time = convertTime(dateTime);
                        int daysLeft = (int) ChronoUnit.DAYS.between(currentDate, localDate);

                        a.add(new ContestClass(name, time, daysLeft, Integer.toString(d), R.drawable.codechef));
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String istTimeString = istTime.format(formatter);

        return istTimeString;
    }
}
