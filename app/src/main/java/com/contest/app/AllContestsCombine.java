package com.contest.app;

import com.contest.app.exception.FetchFailedException;
import com.contest.app.models.ContestClass;
import com.contest.app.scraping.CodechefContestScraper;
import com.contest.app.scraping.CodeforcesContestScraper;
import com.contest.app.scraping.LeetcodeContestScraper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AllContestsCombine {

    List<ContestClass> contests;

    CodeforcesContestScraper codeforcesContestScraper;
    LeetcodeContestScraper leetcodeContestScraper;
    CodechefContestScraper codechefContestScraper;

    public List<ContestClass> getContests() throws FetchFailedException {

        codeforcesContestScraper = new CodeforcesContestScraper();
        leetcodeContestScraper = new LeetcodeContestScraper();
        codechefContestScraper = new CodechefContestScraper();

        final List<ContestClass> codeforces = codeforcesContestScraper.getContests();
        final List<ContestClass> codechef = codechefContestScraper.getContests();
        final List<ContestClass> leetcode = leetcodeContestScraper.getContests();
        contests = new ArrayList<>();

        contests.addAll(codeforces);
        contests.addAll(codechef);
        contests.addAll(leetcode);

        if (codechef == null || leetcode == null || codeforces == null) {
            throw new FetchFailedException("Try after some time", new Throwable("Failed to fetch contests"));
        }
        contests = Sort(contests);
        return contests;
    }

    public List<ContestClass> Sort(List<ContestClass> Contests) {
        Contests.sort((contest1, contest2) -> {
            int s1 = contest1.getDaysLeft();
            int s2 = contest2.getDaysLeft();
            return Integer.compare(s1, s2);
        });
        return Contests;
    }

}
