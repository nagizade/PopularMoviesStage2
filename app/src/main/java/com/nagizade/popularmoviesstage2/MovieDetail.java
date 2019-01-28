package com.nagizade.popularmoviesstage2;

/**
 * Created by Hasan Nagizade on 14 January 2019
 */

import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nagizade.popularmoviesstage2.adapters.ReviewAdapter;
import com.nagizade.popularmoviesstage2.adapters.TrailerAdapter;
import com.nagizade.popularmoviesstage2.model.Movie;
import com.nagizade.popularmoviesstage2.model.MovieDAO;
import com.nagizade.popularmoviesstage2.model.Review;
import com.nagizade.popularmoviesstage2.model.Trailer;
import com.nagizade.popularmoviesstage2.utils.GlobalVariables;
import com.nagizade.popularmoviesstage2.utils.ItemClickListener;
import com.nagizade.popularmoviesstage2.utils.MovieDatabase;
import com.nagizade.popularmoviesstage2.utils.RequestHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.nagizade.popularmoviesstage2.utils.GlobalVariables.DATABASE_NAME;
import static com.nagizade.popularmoviesstage2.utils.GlobalVariables.MOVIE_API_KEY;
import static com.nagizade.popularmoviesstage2.utils.GlobalVariables.MOVIE_IMAGE_URL_BASE;
import static com.nagizade.popularmoviesstage2.utils.GlobalVariables.MOVIE_REVIEWS_URL;
import static com.nagizade.popularmoviesstage2.utils.GlobalVariables.MOVIE_TRAILER_URL;
import static com.nagizade.popularmoviesstage2.utils.GlobalVariables.MOVIE_URL_BASE;

public class MovieDetail extends AppCompatActivity {

    private ImageView           movie_poster,movie_backdrop;
    private TextView            movie_release_date,movie_user_rating,movie_plot,movie_name,movie_status;
    private TextView            reviewsEmptyText,trailersEmptyText;
    private JSONObject          response,response2,response3;
    private MovieDatabase       db;
    private String              poster_Path,backdrop_Path;
    private Boolean             isFavorite = false;
    private ArrayList<Trailer>  trailerList;
    private ArrayList<Review>   reviews;
    private RecyclerView        rvTrailers,rvReviews;
    private Button              addToFavorites;
    private View                short_InfoBlock,plot_Block,trailers_Block,reviews_Block;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        db = Room.databaseBuilder(getApplicationContext(),
                    MovieDatabase.class,DATABASE_NAME).build();

        bindViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Movie Details");

        Intent intent = getIntent();
        final String movieId = intent.getStringExtra("movie_id");

        if(!isOnline()) {
            showDialog("You don't have internet connection");
        }
        getMovieInfo(movieId);
        short_InfoBlock.setVisibility(View.VISIBLE);
        plot_Block.setVisibility(View.VISIBLE);
        getTrailers(movieId);
        trailers_Block.setVisibility(View.VISIBLE);
        getReviews(movieId);
        reviews_Block.setVisibility(View.VISIBLE);

        MovieDAO movieDAO = MovieDatabase.getInstance(this).getMovieDao();
        movieDAO.getMovieTitle(movieId).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null)  isFavorite = true;
                changeIcon(isFavorite);
            }
        });

    }

    private void bindViews() {

        movie_poster        = findViewById(R.id.iv_little_poster);
        movie_release_date  = findViewById(R.id.tv_release_date);
        movie_user_rating   = findViewById(R.id.tv_user_rating);
        movie_plot          = findViewById(R.id.tv_plot);
        movie_name          = findViewById(R.id.tv_title);
        movie_backdrop      = findViewById(R.id.iv_backdrop);
        movie_status        = findViewById(R.id.tv_status);
        rvTrailers          = findViewById(R.id.rv_trailers);
        rvReviews           = findViewById(R.id.rv_reviews);
        addToFavorites      = findViewById(R.id.btn_add_to_favorites);
        reviewsEmptyText    = findViewById(R.id.tv_reviews_not_available);
        trailersEmptyText   = findViewById(R.id.tv_no_trailers);
        short_InfoBlock     = findViewById(R.id.shortInfoBlock);
        plot_Block          = findViewById(R.id.plot_block);
        trailers_Block      = findViewById(R.id.trailersBlock);
        reviews_Block       = findViewById(R.id.reviewsBlock);

    }

    private void getMovieInfo(final String movieId) {

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    RequestHandler requestHandler = new RequestHandler();
                    response = requestHandler.get(GlobalVariables.MOVIE_URL_BASE +movieId+ GlobalVariables.MOVIE_URL_PARAMETER
                                                                                            + GlobalVariables.MOVIE_API_KEY);
                    final String posterPath     = response.getString("poster_path");
                    final String releaseDate    = response.getString("release_date");
                    final String userRating     = response.getString("vote_average");
                    final String plot           = response.getString("overview");
                    final String title          = response.getString("title");
                    final String backdropPath   = response.getString("backdrop_path");
                    final String mv_status      = response.getString("status");
                    poster_Path = posterPath;
                    backdrop_Path = backdropPath;

                    runOnUiThread(() -> {

                        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getApplicationContext());
                        circularProgressDrawable.setStrokeWidth(5f);
                        circularProgressDrawable.setCenterRadius(30f);
                        circularProgressDrawable.start();
                        Glide.with(getApplicationContext())
                                .load(MOVIE_IMAGE_URL_BASE+posterPath)
                                .apply(new RequestOptions()
                                        .placeholder(circularProgressDrawable)
                                        .fitCenter())
                                .into(movie_poster);
                        Glide.with(getApplicationContext())
                                .load(MOVIE_IMAGE_URL_BASE+backdropPath)
                                .apply(new RequestOptions()
                                       .placeholder(circularProgressDrawable))
                                .into(movie_backdrop);

                        movie_release_date.setText(releaseDate);
                        movie_user_rating.setText(userRating);
                       if(plot.isEmpty()) {
                           movie_plot.setText(R.string.plot_not_available_text);
                       } else movie_plot.setText(plot);
                        movie_name.setText(title);
                        movie_status.setText(mv_status);

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

        addToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String movieTitle = movie_name.getText().toString();
                      Movie movie = new Movie(movieId,poster_Path,movieTitle);
                      if(isFavorite) {
                            updateFavorites(movie,false);
                          Toast.makeText(MovieDetail.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                        } else {
                          updateFavorites(movie,true);
                          Toast.makeText(MovieDetail.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                      }
            }
        });
    }

    private void getTrailers(String movieId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                        RequestHandler requestHandler = new RequestHandler();
                        response2 = requestHandler.get(GlobalVariables.MOVIE_URL_BASE+movieId+MOVIE_TRAILER_URL+MOVIE_API_KEY);

                        JSONArray trailers = response2.getJSONArray("results");
                        trailerList = new ArrayList<>();

                          for(int i = 0; i < trailers.length(); i++) {
                                Trailer trailer = new Trailer();
                                trailer.setKey(trailers.getJSONObject(i).getString("key"));
                                trailer.setName(trailers.getJSONObject(i).getString("name"));
                                trailer.setPoster(backdrop_Path);
                                trailerList.add(trailer);
                            }

                            runOnUiThread(() -> {

                                ItemClickListener itemClickListener = new ItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        watchYoutubeVideo(view.getContext(),trailerList.get(position).getKey());
                                    }
                                };
                                if(trailerList.isEmpty()) {
                                    rvTrailers.setVisibility(GONE);
                                    trailersEmptyText.setVisibility(View.VISIBLE);
                                } else {
                                    trailersEmptyText.setVisibility(GONE);
                                    rvTrailers.setVisibility(View.VISIBLE);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                    rvTrailers.setLayoutManager(layoutManager);
                                    TrailerAdapter trailerAdapter = new TrailerAdapter(trailerList, itemClickListener);
                                    rvTrailers.setAdapter(trailerAdapter);
                                }
                            });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }

    private void getReviews(String movieId) {
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestHandler requestHandler = new RequestHandler();
                    response3 = requestHandler.get(MOVIE_URL_BASE+movieId+MOVIE_REVIEWS_URL+MOVIE_API_KEY);

                    JSONArray reviewsList = response3.getJSONArray("results");
                    reviews = new ArrayList<>();

                    for(int i = 0; i < reviewsList.length(); i++) {

                        String reviewAuthor = reviewsList.getJSONObject(i).getString("author");
                        String reviewContent = reviewsList.getJSONObject(i).getString("content");
                        String reviewUrl    = reviewsList.getJSONObject(i).getString("url");
                        Review review = new Review(reviewAuthor,reviewContent,reviewUrl);
                        reviews.add(review);
                    }

                    runOnUiThread(() -> {

                        if(reviews.isEmpty()) {
                            reviewsEmptyText.setVisibility(View.VISIBLE);
                            rvReviews.setVisibility(GONE);
                        } else {
                            reviewsEmptyText.setVisibility(GONE);
                            rvReviews.setVisibility(View.VISIBLE);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            rvReviews.setLayoutManager(layoutManager);
                            ReviewAdapter reviewAdapter = new ReviewAdapter(reviews);
                            rvReviews.setAdapter(reviewAdapter);
                        }

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread3.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnected();
    }


    private void showDialog(String dialogMessage) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this,R.style.Theme_AppCompat_Dialog_Alert);
        builder1.setMessage(dialogMessage);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void updateFavorites(Movie movie,Boolean mustBeAdded) {
        isFavorite = mustBeAdded;
        Thread thread = new Thread() {
            @Override
            public void run() {

                if(isFavorite) {
                    db.getMovieDao().insertMovie(movie);
                } else {
                    db.getMovieDao().deleteMovie(movie);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changeIcon(isFavorite);
                    }
                });
            }

        };
        thread.start();

    }

    private void changeIcon(Boolean isFavorite) {
        if(isFavorite) {
            addToFavorites.setText(R.string.remove_from_favorite);
            addToFavorites.setBackgroundResource(R.drawable.button_shape_selected);
        } else {
                 addToFavorites.setText(R.string.add_to_favorite);
                 addToFavorites.setBackgroundResource(R.drawable.button_shape_not_selected);
        }
    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
