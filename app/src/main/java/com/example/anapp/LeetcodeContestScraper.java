package com.example.anapp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
                    a.add(new ContestClass(name, convertTime(time), Integer.toString(d), R.drawable.leetcode));
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
