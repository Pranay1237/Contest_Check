package com.example.anapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton button;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

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
                        recyclerView.setAdapter(new ContestsAdapter(getApplicationContext(), contests));
                    }
                }
            });
        }
    }
}