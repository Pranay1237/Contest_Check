package com.contest.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.contest.app.models.ContestClass;

import java.util.List;

@Dao
public interface ContestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContestClass contestClass);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ContestClass> contestClasses);

    @Delete
    void delete(ContestClass contestClass);

    @Query("SELECT * FROM Contests")
    LiveData<List<ContestClass>> getAllContests();
}
