package com.nagizade.popularmoviesstage2.model;

/**
 * Created by Hasan Nagizade on 16 January 2019
 */

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface  MovieDAO {

    //Adding a movie to Favorites
    @Insert
    void insertMovie(Movie movie);

    //Deleting movie from Favorites
    @Delete
    void deleteMovie(Movie movie);

    // Getting all favorites from Db
    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> getAllFavorites();

    @Query("SELECT movieTitle FROM Movie WHERE movie_id = :userId")
    LiveData<String> getMovieTitle(String userId);

}
