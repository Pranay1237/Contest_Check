package com.example.anapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LeetcodeContestScraper {

    public List<ContestClass> getContests() {

        OkHttpClient client = new OkHttpClient();
        String url = URLs.LEETCODE_URL;

        Request request = new Request.Builder().url(url).build();

        List<ContestClass> a = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()) {
                assert response.body() != null;
                String responseBody = response.body().string();

                JSONArray jsonArray = new JSONArray(responseBody);

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String name = jsonObject.getString("title");
                    String start = jsonObject.getString("startTime");
                    String duration = jsonObject.getString("duration");
                    String[] res = convertTime(start);

                    a.add(new ContestClass(name, res[0], Integer.parseInt(res[1]), convertSeconds(duration), R.drawable.leetcode));
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

        long timestamp = Long.parseLong(time);
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM hh:mm a");
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        String day = dayOfWeek.toString().substring(0, 3);
        String outputDate = dateTime.format(outputFormatter);
        LocalDateTime currentDate = LocalDateTime.now();
        Duration duration = Duration.between(currentDate, dateTime);
        long daysLeft = duration.toDays();

        return new String[]{day + ", " + outputDate, String.valueOf(daysLeft)};
    }

    public String convertSeconds(String sec) {
        int seconds = Integer.parseInt(sec);
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;

        return String.format("%02d:%02d", hours, minutes);
    }
}
