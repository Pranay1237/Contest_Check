package com.contest.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastOnUI {

    //This class is used to show a toast notification with a message on the UI using Handler
    //TODO Delete this class if we do not use it
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public void showToastOnUIThread(Context context, String message) {
        mHandler.post(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}
