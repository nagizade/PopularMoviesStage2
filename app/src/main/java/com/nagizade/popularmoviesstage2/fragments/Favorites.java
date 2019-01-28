package com.nagizade.popularmoviesstage2.fragments;

/**
 * Created by Hasan Nagizade on 17 January 2019
 */

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagizade.popularmoviesstage2.MovieDetail;
import com.nagizade.popularmoviesstage2.R;
import com.nagizade.popularmoviesstage2.adapters.MovieAdapter;
import com.nagizade.popularmoviesstage2.model.Movie;
import com.nagizade.popularmoviesstage2.model.MovieDAO;
import com.nagizade.popularmoviesstage2.utils.ItemClickListener;
import com.nagizade.popularmoviesstage2.utils.MovieDatabase;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends Fragment {

    private static final String FAVORITES_RECYCLER_KEY = "favorites_key";

    private RecyclerView        rv_favorites;
    private MovieAdapter        movieAdapter;
    private ArrayList<Movie>    favoriteMovies;
    private GridLayoutManager   layoutManager;
    private FragmentActivity    mContext;
    private Boolean             alreadyLoaded = false; // flag to detect if RecyclerView is loaded with elements or not

    public Favorites() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        // If RecyclerView is not filled with elements we are filling it here and setting flag to true;
        if(!alreadyLoaded) {
            getFavoriteMovies();
            alreadyLoaded = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // User moved away or activity is recreated. Setting alreadyLoaded flag to false to get fresh
        // data if user opened MovieDetails activity unfavorited a movie and came back.
        alreadyLoaded = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        rv_favorites = view.findViewById(R.id.rv_favorites);

        // Restoring data after orientation change. If there is no data in savedInstanceState bundle
        // and if RecyclerView is not already filled with data then filling it and setting alreadyLoaded
        // flag to true.
        if (savedInstanceState != null) {
            favoriteMovies  = savedInstanceState.getParcelableArrayList(FAVORITES_RECYCLER_KEY);
            layoutManager   = new GridLayoutManager(getContext(), 2);
            movieAdapter    = new MovieAdapter(favoriteMovies,getItemClickListener());
            rv_favorites.setLayoutManager(layoutManager);
            rv_favorites.setAdapter(movieAdapter);
          } else {
            if(!alreadyLoaded) {
                getFavoriteMovies();
                alreadyLoaded = true;
              }
            }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // To be able to restore our scroll position after orientation change
        // putting our data from RecyclerView into outState bundle
        outState.putSerializable(FAVORITES_RECYCLER_KEY,favoriteMovies);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    // Method to get Favorite movies from Room database and fill RecyclerView with it
    private void getFavoriteMovies() {
        layoutManager = new GridLayoutManager(getContext(), 2);
        rv_favorites.setLayoutManager(layoutManager);
        MovieDAO movieDAO = MovieDatabase.getInstance(getContext()).getMovieDao();
        movieDAO.getAllFavorites().observe(mContext, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {

                // We will need an ArrayList which is accessible from other methods for saving and
                // restoring our data when orientation changes.Converting our movies List into
                // favoriteMovies ArrayList in here
                if (movies != null) {
                    favoriteMovies = new ArrayList<>();
                    favoriteMovies.addAll(movies);
                }

                movieAdapter = new MovieAdapter(favoriteMovies,getItemClickListener());
                rv_favorites.setAdapter(movieAdapter);
                movieAdapter.notifyDataSetChanged();

            }
        });
    }

    private ItemClickListener getItemClickListener() {
        return new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getContext(), MovieDetail.class);
                intent.putExtra("movie_id", favoriteMovies.get(position).getMovie_id());
                startActivity(intent);

            }
        };
    }
}
