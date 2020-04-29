package me.ibrahimsn.viewmodel.room.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import me.ibrahimsn.viewmodel.data.model.Record;
import me.ibrahimsn.viewmodel.room.dao.DaoAboutCanada;

@Database(entities = {Record.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    private static String DB_NAME = "list_demo";
    private static volatile MyDatabase INSTANCE;

    public static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, DB_NAME).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DaoAboutCanada daoAboutCanada();


}
