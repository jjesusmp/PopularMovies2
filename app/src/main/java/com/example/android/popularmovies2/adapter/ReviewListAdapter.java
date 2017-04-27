package com.example.android.popularmovies2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.model.ReviewDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjesusmp on 27/04/2017.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewListAdapterViewHolder>{

    private ArrayList<ReviewDto> reviewList;
    private final Callbacks mCallbacks;

    public ReviewListAdapter(ArrayList<ReviewDto> reviews, Callbacks callbacks) {
        reviewList = reviews;
        mCallbacks = callbacks;
    }

    public interface Callbacks {
        void readReview(ReviewDto review, int position);
    }

    public class ReviewListAdapterViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        TextView mReviewAuthor;
        TextView mReviewContent;
        public ReviewDto mReview;

        public ReviewListAdapterViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            mReviewAuthor = (TextView) itemView.findViewById(R.id.review_author);
            mReviewContent = (TextView) itemView.findViewById(R.id.review_content);
        }
    }

    @Override
    public ReviewListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);

        return new ReviewListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewListAdapterViewHolder holder, int position) {
        final ReviewDto review = reviewList.get(position);
        holder.mReview = review;
        holder.mReviewAuthor.setText(reviewList.get(position).getAuthor());
        holder.mReviewContent.setText(reviewList.get(position).getContent());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.readReview(review, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(reviewList==null){
            return 0;
        } else{
            return reviewList.size();
        }
    }

    public void setReviewsData(List<ReviewDto> reviewsData) {
        reviewList.clear();
        reviewList.addAll(reviewsData);
        notifyDataSetChanged();
    }

    public ArrayList<ReviewDto> getReviews() {
        return reviewList;
    }


}
