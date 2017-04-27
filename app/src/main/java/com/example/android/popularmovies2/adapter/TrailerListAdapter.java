package com.example.android.popularmovies2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.model.ReviewDto;
import com.example.android.popularmovies2.model.TrailerDto;
import com.example.android.popularmovies2.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjesusmp
 */

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.TrailerListAdapterViewHolder>{

    private ArrayList<TrailerDto> trailerList;
    private final Callbacks mCallbacks;

    public TrailerListAdapter(ArrayList<TrailerDto> trailers, Callbacks callbacks) {
        trailerList = trailers;
        mCallbacks = callbacks;
    }

    public interface Callbacks {
        void watchVideo(TrailerDto trailer, int position);
    }

    public class TrailerListAdapterViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        ImageView mTrailerImage;
        public TrailerDto mTrailer;

        public TrailerListAdapterViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            mTrailerImage = (ImageView)itemView.findViewById(R.id.trailer_image);
        }
    }

    @Override
    public TrailerListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_list_item, parent, false);

        return new TrailerListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrailerListAdapterViewHolder holder, int position) {
        final TrailerDto trailer = trailerList.get(position);
        holder.mTrailer = trailer;
        String imgTrailer = "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";

        Picasso.with(holder.mView.getContext())
                .load(imgTrailer)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.mTrailerImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.watchVideo(trailer, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(trailerList==null){
            return 0;
        } else{
            return trailerList.size();
        }
    }

    public void setTrailersData(List<TrailerDto> trailersData) {
        trailerList.clear();
        trailerList.addAll(trailersData);
        notifyDataSetChanged();
    }

    public ArrayList<TrailerDto> getTrailers() {
        return trailerList;
    }


}