package com.nagizade.popularmoviesstage2.utils;

/**
 * Created by Hasan Nagizade on 16 January 2019
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nagizade.popularmoviesstage2.model.Movie;
import com.nagizade.popularmoviesstage2.model.MovieDAO;

@Database(entities = {Movie.class},version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase INSTANCE;
    public abstract MovieDAO getMovieDao();
    private Context context;
    public static MovieDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),MovieDatabase.class,GlobalVariables.DATABASE_NAME)
                           .build();
        }
        return INSTANCE;
    }
}
