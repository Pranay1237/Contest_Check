package com.example.anapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.imageView.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
