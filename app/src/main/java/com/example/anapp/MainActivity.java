package com.example.anapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

        button = findViewById(R.id.EditButton);
        button1 = findViewById(R.id.getContest);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new thread1().start();
            }
        });
    }

    class thread1 extends Thread {
        public void run() {
            super.run();
            CodeforcesContestScraper codeforcesContestScraper = new CodeforcesContestScraper();
            final List<Codeforces> contests = codeforcesContestScraper.getContests();

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