package com.example.suhaib.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.suhaib.popularmovies.Movie;

import java.util.List;

/**
 * Created by suhaib on 10/9/18.
 */
@Dao
public interface MoviesDao {
    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM Movie where id = :id")
    List<Movie> checkMovie(int id);
}
