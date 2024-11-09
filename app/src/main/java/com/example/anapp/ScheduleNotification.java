package com.example.anapp;

import android.content.Context;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ScheduleNotification {

    public ScheduleNotification(Context context, int year, int month, int day, int hour, int minute, int seconds) {
        Calendar currentTime = Calendar.getInstance();

        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.YEAR, year);
        targetTime.set(Calendar.MONTH, month - 1);
        targetTime.set(Calendar.DAY_OF_MONTH, day);
        targetTime.set(Calendar.HOUR_OF_DAY, hour);
        targetTime.set(Calendar.MINUTE, minute);
        targetTime.set(Calendar.SECOND, seconds);

        long delay = targetTime.getTimeInMillis() - currentTime.getTimeInMillis();
        if (delay <= 0) {
            return;
        }

        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build();

        WorkManager.getInstance(context).enqueue(notificationWork);

    }
}
