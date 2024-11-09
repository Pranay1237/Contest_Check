package com.contest.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anapp.R;
import com.service.notification.ScheduleNotification;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progressBar;
    AllContestsCombine allContestsCombine;
    List<ContestClass> contests;
    ExecutorService executorService;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        setSupportActionBar(toolbar);
        progressBar.setVisibility(View.VISIBLE);

        showContests();
        getNotificationPermission();
        new ScheduleNotification(getApplicationContext(), 2024, 11, 10, 1, 16, 30);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            Log.i("MainActivity", "onRefresh called from SwipeRefreshLayout");
            showContests();
            Toast.makeText(this, "Refresh successful", Toast.LENGTH_SHORT).show();
        });
    }

    private void showContests() {

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
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    public void getNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }
}