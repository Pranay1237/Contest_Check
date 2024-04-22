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

                JSONArray jsonArray = new JSONArray(responseBody);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String code = jsonObject.getString("contest_code");
                    String name = jsonObject.getString("contest_name");
                    String start = jsonObject.getString("contest_start_date_iso");
                    String duration = jsonObject.getString("contest_duration");
                    String res[] = convertTime(start);
                    String link = "https://www.codechef.com/"+code;

                    a.add(new ContestClass(name, res[0], Integer.parseInt(res[1]), duration, R.drawable.codechef));
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

    public String[] convertTime(String time) {
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(time, isoFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM hh:mm a");
        String normalTime = zonedDateTime.format(outputFormatter);

        DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
        String day = dayOfWeek.toString().substring(0, 3);

        long daysLeft = ZonedDateTime.now().until(zonedDateTime, ChronoUnit.DAYS);

        return new String[]{day + ", " + normalTime, String.valueOf(daysLeft)};
    }
}
