package com.example.anapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                    String time = jsonObject.getString("start_time");
                    String date = time.substring(0, 10);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    LocalDate localDate = LocalDate.parse(date, formatter);
                    LocalDate currentDate = LocalDate.now();

                    if(localDate.isAfter(currentDate)) {
                        String name = jsonObject.getString("name");
                        String duration = jsonObject.getString("duration");
                        int d = Integer.parseInt(duration)/3600;
                        a.add(new ContestClass(name, time, Integer.toString(d), R.drawable.codechef));
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
}
