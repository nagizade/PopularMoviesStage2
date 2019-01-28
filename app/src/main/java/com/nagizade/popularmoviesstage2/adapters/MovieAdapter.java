package com.nagizade.popularmoviesstage2.adapters;

/**
 * Created by Hasan Nagizade on 14 January 2019
 */

import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nagizade.popularmoviesstage2.R;
import com.nagizade.popularmoviesstage2.model.Movie;
import com.nagizade.popularmoviesstage2.utils.ItemClickListener;

import java.util.List;

import static com.nagizade.popularmoviesstage2.utils.GlobalVariables.MOVIE_IMAGE_URL_BASE;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies;
    private ItemClickListener mListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView iv_poster;
        private ItemClickListener mListener;


        ViewHolder(View itemView,ItemClickListener listener) {
            super(itemView);
            mListener = listener;
            iv_poster = itemView.findViewById(R.id.img_poster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v, getAdapterPosition());
        }
    }

    public MovieAdapter(List<Movie> movies,ItemClickListener listener) {
        this.movies = movies;
        this.mListener = listener;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.movie_row,parent,false);

        return new MovieAdapter.ViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder,int position) {
        Movie movie = movies.get(position);
        String imagepath = movie.getPoster();

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(holder.itemView.getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        Glide.with(holder.itemView)
                .load(MOVIE_IMAGE_URL_BASE+imagepath)
                .apply(new RequestOptions()
                       .placeholder(circularProgressDrawable))
                .into(holder.iv_poster);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
