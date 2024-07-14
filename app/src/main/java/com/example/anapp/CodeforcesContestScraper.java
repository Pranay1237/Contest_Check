package com.example.anapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CodeforcesContestScraper {

    public List<ContestClass> getContests() {

        OkHttpClient client = new OkHttpClient();
        String url = URLs.CODEFORCES_URL;

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
                    String link = jsonObject.getString("register");
                    String[] res = convertTime(start);

                    a.add(new ContestClass(name, res[0], Integer.parseInt(res[1]), duration, R.drawable.codeforces));
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

    public String[] convertTime(String time) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM hh:mm a");

        LocalDateTime dateTime = LocalDateTime.parse(time, inputFormatter);

        String outputDate = dateTime.format(outputFormatter);

        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        String day = dayOfWeek.toString().substring(0, 3);

        LocalDateTime currentDate = LocalDateTime.now();
        long daysLeft = ChronoUnit.DAYS.between(currentDate, dateTime);

        String daysLeftString = String.valueOf(daysLeft);

        return new String[]{day + ", " + outputDate, daysLeftString};
    }
}
