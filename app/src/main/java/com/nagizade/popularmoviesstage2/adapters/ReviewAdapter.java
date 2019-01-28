package com.nagizade.popularmoviesstage2.adapters;

/**
 * Created by Hasan Nagizade on 23 January 2019
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nagizade.popularmoviesstage2.R;
import com.nagizade.popularmoviesstage2.model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<Review> reviews;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reviewAuthor;
        private TextView reviewText;
        private Button   readMore;


        ViewHolder(View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.tv_author);
            reviewText   = itemView.findViewById(R.id.tv_review);
            readMore     = itemView.findViewById(R.id.btn_read_more);
        }

    }

    public ReviewAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.review_row,parent,false);

        return new ReviewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder,int position) {
        Review review = reviews.get(position);

        holder.reviewAuthor.setText(review.getReview_author());

        String reviewLong = review.getReview_content();
        String reviewShort = "";
        if(reviewLong.length() < 200) {
            reviewShort = reviewLong;
        } else {
            reviewShort = reviewLong.substring(0,200)+"...";
            holder.readMore.setVisibility(View.VISIBLE);
        }
        holder.reviewText.setText(reviewShort);
        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlAdress = review.getUrl_adress();
                Context context = holder.readMore.getContext();

                Intent reviewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAdress));
                context.startActivity(reviewIntent);
            }
        });
        

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
