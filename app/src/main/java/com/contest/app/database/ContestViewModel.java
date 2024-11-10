package com.contest.app.database;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.contest.app.models.ContestClass;

import java.util.List;

public class ContestViewModel extends AndroidViewModel {
    private final ContestRepository repository;
    private final LiveData<List<ContestClass>> allContests;

    public ContestViewModel(Application application) {
        super(application);
        repository = new ContestRepository(application.getApplicationContext());
        allContests = repository.getAllContests();
    }

    public LiveData<List<ContestClass>> getAllContests() {
        return allContests;
    }

    public void insert(ContestClass contestClass) {
        repository.insert(contestClass);
    }

    public void insertAll(List<ContestClass> contestClasses) {
        repository.insertAll(contestClasses);
    }
}
