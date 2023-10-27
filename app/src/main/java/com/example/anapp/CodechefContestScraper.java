package com.example.anapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CodechefContestScraper {

    public void getContests() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://kontests.net/api/v1/code_chef";

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()) {
                String responseBody = response.body().string();

                JSONArray jsonArray = new JSONArray(responseBody);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("start_time");
                    String time = name.substring(0, 10);
                    System.out.println("Time: " + time);
                }

            } else {
                System.out.println("Response was not Successful. Response code : " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
