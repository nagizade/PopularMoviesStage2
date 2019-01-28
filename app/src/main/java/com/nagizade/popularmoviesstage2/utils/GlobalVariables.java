package com.nagizade.popularmoviesstage2.utils;

/**
 * Created by Hasan Nagizade on 15 January 2019
 */

public class GlobalVariables {

    // You must put your API KEY in here
    public static final String MOVIE_API_KEY = "";

    // Parameter string for API KEY
    public static final String MOVIE_URL_PARAMETER = "?api_key=";

    // Base URL for movies
    public static final String MOVIE_URL_BASE = "http://api.themoviedb.org/3/movie/";

    // Base url for images
    public static final String MOVIE_IMAGE_URL_BASE="http://image.tmdb.org/t/p/w342/";

    // Base URL for Movie trailers
    public static final String MOVIE_TRAILER_URL="/videos?api_key=";

    // Base URL for Movie reviews
    public static final String MOVIE_REVIEWS_URL="/reviews?api_key=";

    // URL for Popular movies
    public final static String URL_POPULAR="http://api.themoviedb.org/3/movie/popular?api_key=";

    // URL for Top Rated movies
    public final static String URL_HIGH_RATE = "http://api.themoviedb.org/3/movie/top_rated?api_key=";

    // Name for local database to store favourite movies
    public final static String DATABASE_NAME = "movie_database";



}
