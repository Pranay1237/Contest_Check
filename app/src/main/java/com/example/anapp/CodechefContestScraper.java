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
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CodechefContestScraper {

    public List<ContestClass> getContests() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://contests-backend.up.railway.app/codechef";

        Request request = new Request.Builder().url(url).build();

        List<ContestClass> a = new ArrayList<>();

        System.out.println("yes");

        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()) {
                String responseBody = response.body().string();

//                System.out.println(responseBody);

                JSONArray jsonArray = new JSONArray(responseBody);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String code = jsonObject.getString("contest_code");
                    String name = jsonObject.getString("contest_name");
                    String start = jsonObject.getString("contest_start_date");
                    String duration = jsonObject.getString("contest_duration");
                    String link = "https://www.codechef.com/"+code;

                    a.add(new ContestClass(name, start, link, 0, duration, R.drawable.codechef));
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

//    public String convertTime(String time) {
//        Instant instant = Instant.parse(time);
//
//        ZoneId istZone = ZoneId.of("Asia/Kolkata");
//        ZonedDateTime istTime = instant.atZone(istZone);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm");
//        String istTimeString = istTime.format(formatter);
//
//        return istTimeString;
//    }
}
