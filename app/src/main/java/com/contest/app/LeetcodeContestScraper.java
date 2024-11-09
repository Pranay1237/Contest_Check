package com.contest.app;

import com.example.anapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

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
                String responseBody = response.body().string();

                JSONArray jsonArray = new JSONArray(responseBody);

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String name = jsonObject.getString("name");
                    String start = jsonObject.getString("start");
                    JSONObject duration = jsonObject.getJSONObject("duration");
                    String link = jsonObject.getString("register");
                    JSONObject startsIn = jsonObject.getJSONObject("startsIn");
                    int days = startsIn.getInt("days");
                    int hours = startsIn.getInt("hours");
                    int minutes = startsIn.getInt("minutes");
                    int durationHours = duration.getInt("hours");
                    int durationMinutes = duration.getInt("minutes");
                    String durationString = (durationHours == 0 ? "" : (durationHours + "h ")) + (durationMinutes == 0 ? "" : (durationMinutes + "m"));
                    String resultantDays = (days == 0 ? "" : (days + "d ")) + (hours == 0 ? "" : (hours + "h ")) + (minutes == 0 ? "" : (minutes + "m"));

                    a.add(new ContestClass(name, start, days, resultantDays, durationString, R.drawable.leetcode));
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
}
