package com.nagizade.popularmoviesstage2.adapters;

/**
 * Created by Hasan Nagizade on 22 January 2019
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nagizade.popularmoviesstage2.R;
import com.nagizade.popularmoviesstage2.model.Trailer;
import com.nagizade.popularmoviesstage2.utils.ItemClickListener;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private ArrayList<Trailer> trailers;
    private ItemClickListener mListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView  trailer_name;
        private ItemClickListener mListener;


        ViewHolder(View itemView,ItemClickListener listener) {
            super(itemView);
            mListener = listener;
            trailer_name  = itemView.findViewById(R.id.tv_trailer_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v, getAdapterPosition());
        }
    }

    public TrailerAdapter(ArrayList<Trailer> trailers,ItemClickListener listener) {
        this.trailers = trailers;
        this.mListener = listener;
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.trailer_row,parent,false);

        return new TrailerAdapter.ViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder,int position) {
        Trailer trailer = trailers.get(position);
        holder.trailer_name.setText(trailer.getName());

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
