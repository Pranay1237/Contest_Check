package com.contest.app.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ContestViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public ContestViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ContestViewModel.class)) {
            return (T) new ContestViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
