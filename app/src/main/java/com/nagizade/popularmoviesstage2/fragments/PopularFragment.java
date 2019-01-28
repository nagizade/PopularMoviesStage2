package com.nagizade.popularmoviesstage2.fragments;

/**
 * Created by Hasan Nagizade on 17 January 2019
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagizade.popularmoviesstage2.MovieDetail;
import com.nagizade.popularmoviesstage2.R;
import com.nagizade.popularmoviesstage2.adapters.MovieAdapter;
import com.nagizade.popularmoviesstage2.model.Movie;
import com.nagizade.popularmoviesstage2.utils.GlobalVariables;
import com.nagizade.popularmoviesstage2.utils.ItemClickListener;
import com.nagizade.popularmoviesstage2.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PopularFragment extends Fragment {

    private static final String RECYCLER_ELEMENTS_KEY="recycler_elements";

    private RecyclerView        rv_popular;
    private JSONObject          response;
    private ArrayList<Movie>    movies;
    private MovieAdapter        movieAdapter;
    private GridLayoutManager   layoutManager;
    private FragmentActivity     mContext;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        rv_popular = view.findViewById(R.id.rv_popular);

        // Restoring data after orientation change. If there is no data to restore then getting
        // new data
        if(savedInstanceState != null) {

            movies              = savedInstanceState.getParcelableArrayList(RECYCLER_ELEMENTS_KEY);
            movieAdapter        = new MovieAdapter(movies, getItemClickListener());
            layoutManager       = new GridLayoutManager(getContext(), 2);
            rv_popular.setLayoutManager(layoutManager);
            rv_popular.setAdapter(movieAdapter);
        } else {
            getPopularMovies();
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Putting our data into outState bundle to restore after orientation change
        outState.putSerializable(RECYCLER_ELEMENTS_KEY,movies);
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

    // Method to get PopularMovies
    private void getPopularMovies() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    RequestHandler requestHandler = new RequestHandler();
                    response = requestHandler.get(GlobalVariables.URL_POPULAR+ GlobalVariables.MOVIE_API_KEY);

                    JSONArray results = response.getJSONArray("results");
                    movies = new ArrayList<>();

                    for(int i = 0; i < results.length(); i++) {

                        JSONObject movie    = results.getJSONObject(i);
                        String poster       = movie.getString("poster_path");
                        String movieId      = movie.getString("id");
                        String movieTitle   = movie.getString("title");
                        movies.add(new Movie(movieId,poster,movieTitle));
                    }

                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            movieAdapter    = new MovieAdapter(movies, getItemClickListener());
                            layoutManager   = new GridLayoutManager(getContext(), 2);
                            rv_popular.setLayoutManager(layoutManager);
                            rv_popular.setAdapter(movieAdapter);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private ItemClickListener getItemClickListener() {
        return new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getContext(), MovieDetail.class);
                intent.putExtra("movie_id", movies.get(position).getMovie_id());
                startActivity(intent);

            }
        };
    }

}
