package com.example.anapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progressBar;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        progressBar = findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);

        progressBar.setVisibility(View.VISIBLE);

        new thread1().start();
    }

    private void showToastOnUIThread(String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    class thread1 extends Thread {
        public void run() {
            super.run();

            CodeforcesContestScraper codeforcesContestScraper = new CodeforcesContestScraper();
            LeetcodeContestScraper leetcodeContestScraper = new LeetcodeContestScraper();
            CodechefContestScraper codechefContestScraper = new CodechefContestScraper();

            final List<ContestClass> codeforces = codeforcesContestScraper.getContests();
            final List<ContestClass> codechef = codechefContestScraper.getContests();
            final List<ContestClass> leetcode = leetcodeContestScraper.getContests();

            if(codechef == null || leetcode == null || codeforces == null) {
                showToastOnUIThread("Some error occurred. Try again after some time");
            }

            if(codeforces != null && codechef != null) {
                codeforces.addAll(codechef);
            }

            if(codeforces != null && leetcode != null) {
                codeforces.addAll(leetcode);
            }

            // Update the UI on the main thread using runOnUiThread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codeforces == null) {
                        showToastOnUIThread("Some error occurred");
                        System.out.println("error");
                    }
                    else {
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        List<ContestClass> contests = Sort(codeforces);

                        recyclerView.setAdapter(new ContestsAdapter(getApplicationContext(), contests));
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    public List<ContestClass> Sort(List<ContestClass> Contests) {
        Collections.sort(Contests, new Comparator<ContestClass>() {
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
                return d1.compareTo(d2);
            }
        });
        return Contests;
    }
}