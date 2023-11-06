package com.example.anapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ContestsAdapter extends RecyclerView.Adapter<ContestsViewHolder> {

    Context context;
    List<ContestClass> items;

    public ContestsAdapter(Context context, List<ContestClass> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ContestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContestsViewHolder(LayoutInflater.from(context).inflate(R.layout.contestsview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContestsViewHolder holder, int position) {
        holder.contestName.setText(items.get(position).getContestName());
        holder.dateTime.setText(items.get(position).getStartTime() + " " + items.get(position).getDay());
        holder.duration.setText(items.get(position).getDuration() + " hrs");
        holder.imageView.setImageResource(items.get(position).getImage());

        int left = items.get(position).getDaysLeft();
        if(left == 0) {
            String dateTime = items.get(position).getStartTime();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy HH:mm");

            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime givenDateTime = LocalDateTime.parse(dateTime, formatter);

            Duration duration = Duration.between(currentDateTime, givenDateTime);

            String ans = "Starts in " + (int) duration.toHours() + ":" + (int) duration.toMinutes()%60;

            holder.daysLeft.setText(ans);
        } else if(left == 1) {
            holder.daysLeft.setText("Tomorrow");
        } else {
            holder.daysLeft.setText("In " + left + " days");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
