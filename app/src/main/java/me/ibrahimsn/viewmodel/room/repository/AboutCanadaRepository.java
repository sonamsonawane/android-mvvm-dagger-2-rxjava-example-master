package me.ibrahimsn.viewmodel.room.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import me.ibrahimsn.viewmodel.data.model.Record;
import me.ibrahimsn.viewmodel.room.db.MyDatabase;


public class AboutCanadaRepository {

    private MyDatabase myDatabase;

    public AboutCanadaRepository(Context context) {
        myDatabase = MyDatabase.getDatabase(context);
    }


    public void insertAll(final List<Record> dataModels) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.daoAboutCanada().insertAll(dataModels);
                return null;
            }
        }.execute();
    }

    public LiveData<List<Record>> getAll() {
        return myDatabase.daoAboutCanada().getAll();
    }
}