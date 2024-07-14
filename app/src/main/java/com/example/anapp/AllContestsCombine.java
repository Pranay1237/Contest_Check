package com.example.anapp;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AllContestsCombine {

    Context context = null;
    ToastOnUI toastOnUI;
    List<ContestClass> contests;

    CodeforcesContestScraper codeforcesContestScraper;
    LeetcodeContestScraper leetcodeContestScraper;
    CodechefContestScraper codechefContestScraper;

    //Passing the Context as a parameter because to show a Toast notification we need the context
    public AllContestsCombine(Context context) {
        this.context = context;
    }

    public List<ContestClass> getContests() {

        toastOnUI = new ToastOnUI();

        codeforcesContestScraper = new CodeforcesContestScraper();
        leetcodeContestScraper = new LeetcodeContestScraper();
        codechefContestScraper = new CodechefContestScraper();

        final List<ContestClass> codeforces = codeforcesContestScraper.getContests();
        final List<ContestClass> codechef = codechefContestScraper.getContests();
        final List<ContestClass> leetcode = leetcodeContestScraper.getContests();

        contests.addAll(codeforces);
        contests.addAll(codechef);
        contests.addAll(leetcode);

        if (codechef == null || leetcode == null || codeforces == null) {
            toastOnUI.showToastOnUIThread(context, "Some error occurred. Try again after some time");
        }
        contests = Sort(contests);
        return contests;
    }

    public List<ContestClass> Sort(List<ContestClass> Contests) {
        Contests.sort(new Comparator<ContestClass>() {
            @Override
            public int compare(ContestClass o1, ContestClass o2) {
                String s1 = o1.getStartTime().substring(5);
                String s2 = o2.getStartTime().substring(5);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM hh:mm a");

                Date d1, d2;

                try {
                    d1 = dateFormat.parse(s1);
                    d2 = dateFormat.parse(s2);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                assert d1 != null;
                return d1.compareTo(d2);
            }
        });
        return Contests;
    }

}
