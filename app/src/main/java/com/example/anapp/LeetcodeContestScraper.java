package com.example.anapp;

import android.widget.Toast;

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
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LeetcodeContestScraper {

    public List<ContestClass> getContests() {

        List<ContestClass> a = new ArrayList<>();

        try {
            String url = "https://leetcode.com/contest/";
            Document document = Jsoup.connect(url).get();

            Element Weekly = document.getElementsByClass("truncate").get(0);
            Element Biweekly = document.getElementsByClass("truncate").get(1);

            String weeklyContestName = Weekly.text();
            String biWeeklyContestName = Biweekly.text();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm");

            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime sundayDateTime = currentDateTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).withHour(8).withMinute(30).withSecond(0).withNano(0);

            String weeklyContestDate = sundayDateTime.format(formatter);

            Duration duration = Duration.between(currentDateTime, sundayDateTime);

            int left = (int)duration.toDays();
            String dur = "01:30";

            a.add(new ContestClass(weeklyContestName, weeklyContestDate, left, dur, R.drawable.leetcode));

            String contestNum = weeklyContestName.substring(15, 18);
            if(Integer.parseInt(contestNum)%2 == 1) {
                LocalDateTime saturdayDateTime = currentDateTime.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).withHour(20).withMinute(30).withSecond(0).withNano(0);

                String biweeklyContestDate = saturdayDateTime.format(formatter);

                duration = Duration.between(currentDateTime, saturdayDateTime);

                left = (int)duration.toDays();
                dur = "01:30";

                a.add(new ContestClass(biWeeklyContestName, biweeklyContestDate, left, dur, R.drawable.leetcode));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
}
