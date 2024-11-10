package com.contest.app.worker;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class ScheduleWorker {

    public static void schedule() {
        PeriodicWorkRequest dailyWorkRequest = new PeriodicWorkRequest.Builder(
                DataFetchWorker.class, 12, TimeUnit.HOURS).build();

        WorkManager.getInstance().enqueueUniquePeriodicWork(
                "DailyDataFetch",
                ExistingPeriodicWorkPolicy.KEEP,
                dailyWorkRequest
        );
    }
}
