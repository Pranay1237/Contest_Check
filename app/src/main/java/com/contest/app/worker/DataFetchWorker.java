package com.contest.app.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.contest.app.AllContestsCombine;
import com.contest.app.database.ContestRepository;
import com.contest.app.exception.FetchFailedException;
import com.contest.app.models.ContestClass;

import java.util.List;

public class DataFetchWorker extends Worker {

    AllContestsCombine allContestsCombine;
    List<ContestClass> contests;
    ContestRepository contestRepository;

    public DataFetchWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            allContestsCombine = new AllContestsCombine();
            contests = allContestsCombine.getContests();
            Log.i("DataFetchWorker", "Contests fetched");
            UpdateDatabase();
        } catch (FetchFailedException e) {
            Log.e("Failed to fetch contests", e.getMessage());
            return Result.failure();
        }
        return Result.success();
    }

    public void UpdateDatabase() {
        contestRepository = new ContestRepository(getApplicationContext());
        contestRepository.insertAll(contests);
    }
}
