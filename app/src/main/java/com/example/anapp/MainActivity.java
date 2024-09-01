package com.example.anapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progressBar;
    AllContestsCombine allContestsCombine;
    List<ContestClass> contests;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        progressBar = findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);

        showContests();
    }

    private void showContests() {

        progressBar.setVisibility(View.VISIBLE);

        allContestsCombine = new AllContestsCombine(MainActivity.this);
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                contests = allContestsCombine.getContests();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(new ContestsAdapter(getApplicationContext(), contests));
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }
}