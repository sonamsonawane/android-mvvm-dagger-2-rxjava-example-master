package me.ibrahimsn.viewmodel.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

import me.ibrahimsn.viewmodel.data.model.Record;


@Dao
public interface DaoAboutCanada {
    @Query("SELECT * FROM Record")
    LiveData<List<Record>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Record> records);

    @Query("DELETE from Record")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Record task);

}

