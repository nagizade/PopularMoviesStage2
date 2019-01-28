package com.nagizade.popularmoviesstage2.model;

/**
 * Created by Hasan Nagizade on 14 January 2019
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class Movie implements Parcelable {
    private String poster;
    @PrimaryKey
    @NonNull
    private String movie_id;
    private String movieTitle;

    public Movie(String movie_id,String poster,String movieTitle) {
        this.movie_id = movie_id;
        this.poster = poster;
        this.movieTitle = movieTitle;
    }

    protected Movie(Parcel in) {
        poster = in.readString();
        movie_id = in.readString();
        movieTitle = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster);
        dest.writeString(movie_id);
        dest.writeString(movieTitle);
    }
}
