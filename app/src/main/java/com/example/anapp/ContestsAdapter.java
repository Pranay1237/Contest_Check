package com.example.anapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

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
        holder.dateTime.setText(items.get(position).getStartTime());
        holder.duration.setText(items.get(position).getDuration() + " hrs");
        holder.imageView.setImageResource(items.get(position).getImage());

        int left = items.get(position).getDaysLeft();
        if(left == 0) {
            String time = items.get(position).getStartTime();
            time = time.substring(time.length() - 5);
            int hours = Integer.parseInt(time.substring(0, 2));
            int mins = Integer.parseInt(time.substring(3, 5));

            LocalTime localTime = LocalTime.of(hours, mins);
            LocalTime currentTime = LocalTime.now();

            Duration duration = Duration.between(currentTime, localTime);

            int h = (int)duration.toHours();
            int m = (int)(duration.toMinutes() % 60);

            String ans;

            if(h == 0) {
                ans = "In " + m+ " min";
            } else {
                ans = "In " + h + ":" + m+ " hrs";
            }
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
