package com.example.suhaib.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.suhaib.popularmovies.Movie;

/**
 * Created by suhaib on 10/9/18.
 */
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MoviesDao getMoviesDao();

    private static final String DATABASE = "db";
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , AppDatabase.class, DATABASE)
                    .build();
        }
        return instance;
    }
}
