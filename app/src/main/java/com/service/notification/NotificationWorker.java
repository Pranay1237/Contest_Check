package com.service.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.anapp.R;

public class NotificationWorker extends Worker {

    private static final String CHANNEL_ID = "notify_channel";
    private static final int NOTIFICATION_ID = 1;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        sendNotification();
        return Result.success();
    }

    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle("Contest Remainder")
                .setContentText("Get Ready for the codechef contest")
                .setSmallIcon(R.drawable.codechef)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
