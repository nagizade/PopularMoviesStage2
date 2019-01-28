package com.nagizade.popularmoviesstage2.model;

/**
 * Created by Hasan Nagizade on 16 January 2019
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MovieObject {
    @PrimaryKey
    @NonNull
    private String movieId;
    private String posterPath;

    private String movieTitle;

    public MovieObject(String movieId,String posterPath,String movieTitle) {
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.movieTitle = movieTitle;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
