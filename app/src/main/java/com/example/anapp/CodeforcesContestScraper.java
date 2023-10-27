package com.example.anapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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
                String Duration = col.get(3).text();

                ans.add(new ContestClass(contestName, startTime, Duration, R.drawable.codeforces2));
            }
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
