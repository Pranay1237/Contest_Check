package com.example.anapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class LeetcodeContestScraper {

    public void getContests() {
        try {
            String url = "https://leetcode.com/contest/";
            Document document = Jsoup.connect(url).get();

            Element contestDiv1 = document.getElementsByClass("truncate").get(0);
            Element contestDiv2 = document.getElementsByClass("truncate").get(1);

            Elements span1 = contestDiv1.select("span");
            Elements span2 = contestDiv2.select("span");

            String WeeklyContest = span1.text();
            String BiweeklyContest = span2.text();

            System.out.println(WeeklyContest);
            System.out.println(BiweeklyContest);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
