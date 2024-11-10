package com.contest.app.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.contest.app.database.dao.ContestDao;
import com.contest.app.models.ContestClass;

import java.util.List;

public class ContestRepository {

    private final ContestDao contestDao;
    private final LiveData<List<ContestClass>> allContests;

    public ContestRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        contestDao = db.contestDao();
        allContests = contestDao.getAllContests();
    }

    public LiveData<List<ContestClass>> getAllContests() {
        return allContests;
    }

    public void insert(ContestClass contestClass) {
        AppDatabase.databaseWriteExecutor.execute(() -> contestDao.insert(contestClass));
    }

    public void insertAll(List<ContestClass> contestClasses) {
        AppDatabase.databaseWriteExecutor.execute(() -> contestDao.insertAll(contestClasses));
    }
}
