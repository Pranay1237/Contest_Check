package com.example.anapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

public class CodeforcesContestScraper {

    public List<ContestClass> getContests() {
        try {
            String url = "https://codeforces.com/contests";
            Document document = Jsoup.connect(url).get();

            Element contestTable = document.select("table").get(0);
            Elements rows = contestTable.select("tr");

            List<ContestClass> ans = new ArrayList<>();

            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements col = row.select("td");

                String contestName = col.get(0).text();
                String startTime = col.get(2).text();
                String duration = col.get(3).text();

                String convertedTime = convertTime(startTime);

                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm");

                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime givenDateTime = LocalDateTime.parse(convertedTime, formatter1);
                Duration duration1 = Duration.between(currentDateTime, givenDateTime);

                ans.add(new ContestClass(contestName, convertedTime, (int)duration1.toDays(), duration, R.drawable.codeforces));
            }
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertTime(String time) {

        DateTimeFormatter moscowFormatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm", Locale.ENGLISH);
        ZonedDateTime moscowDateTime = ZonedDateTime.parse(time, moscowFormatter.withZone(ZoneId.of("Europe/Moscow")));

        ZonedDateTime istDateTime = moscowDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));

        DateTimeFormatter istFormatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm", Locale.ENGLISH);
        String istDateTimeStr = istDateTime.format(istFormatter);

        return istDateTimeStr;
    }
}
