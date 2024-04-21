package com.example.anapp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CodeforcesContestScraper {

    public List<ContestClass> getContests() {

        OkHttpClient client = new OkHttpClient();
        String url = "https://contests-backend.up.railway.app/codeforces";

        Request request = new Request.Builder().url(url).build();

        List<ContestClass> a = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()) {
                String responseBody = response.body().string();

                JSONArray jsonArray = new JSONArray(responseBody);

                for(int i = 0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String name = jsonObject.getString("name");
                    String start = jsonObject.getString("start");
                    String duration = jsonObject.getString("duration");
                    String left = jsonObject.getString("startsIn");
                    String link = jsonObject.getString("register");

                    a.add(new ContestClass(name, start, link, 0, duration, R.drawable.codeforces));
                }
                return a;
            } else {
                System.out.println("Response was not Successful. Response code : " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public String convertTime(String time) {
//
//        DateTimeFormatter moscowFormatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm", Locale.ENGLISH);
//        ZonedDateTime moscowDateTime = ZonedDateTime.parse(time, moscowFormatter.withZone(ZoneId.of("Europe/Moscow")));
//
//        ZonedDateTime istDateTime = moscowDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
//
//        DateTimeFormatter istFormatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm", Locale.ENGLISH);
//        String istDateTimeStr = istDateTime.format(istFormatter);
//
//        return istDateTimeStr;
//    }
}
