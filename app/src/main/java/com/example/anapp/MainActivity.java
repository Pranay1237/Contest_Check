package com.example.anapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progressBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.loginButton);
        setSupportActionBar(toolbar);

        progressBar.setVisibility(View.VISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), cfLogin.class);
                startActivity(intent);
            }
        });

        new thread1().start();
    }

    class thread1 extends Thread {
        public void run() {
            super.run();

            CodeforcesContestScraper codeforcesContestScraper = new CodeforcesContestScraper();
            LeetcodeContestScraper leetcodeContestScraper = new LeetcodeContestScraper();
            CodechefContestScraper codechefContestScraper = new CodechefContestScraper();

            final List<ContestClass> contests = codeforcesContestScraper.getContests();
            final List<ContestClass> a = codechefContestScraper.getContests();
            final List<ContestClass> b = leetcodeContestScraper.getContests();

            if(a == null || b == null || contests == null) {
                Toast.makeText(getApplicationContext(), "Some error occurred. Try again after some time", Toast.LENGTH_SHORT).show();
            }

            if(contests != null && a != null) {
                contests.addAll(a);
            }

            if(contests != null && b != null) {
                contests.addAll(b);
            }

            // Update the UI on the main thread using runOnUiThread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (contests == null) {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                        System.out.println("error");
                    }
                    else {
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        Comparator<ContestClass> daysCompare = Comparator.comparingInt(ContestClass::getDaysLeft);
                        contests.sort(daysCompare);

                        recyclerView.setAdapter(new ContestsAdapter(getApplicationContext(), contests));
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}