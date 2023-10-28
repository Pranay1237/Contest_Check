package com.example.anapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContestsViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView contestName, dateTime, duration, daysLeft;
    public ContestsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        contestName = itemView.findViewById(R.id.contestname);
        dateTime = itemView.findViewById(R.id.datetime);
        duration = itemView.findViewById(R.id.duration);
        daysLeft = itemView.findViewById(R.id.daysLeft);
    }
}
