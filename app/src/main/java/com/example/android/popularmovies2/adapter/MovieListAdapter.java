package com.example.android.popularmovies2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.model.MovieDto;
import com.example.android.popularmovies2.utils.Constants;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

/**
 * Created by jjesusmp
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListAdapterViewHolder> {

    private ArrayList<MovieDto> mMovieListData;
    final private MovieListAdapterOnClickHandler mClickHandler;

    public interface MovieListAdapterOnClickHandler{
        void onClick(MovieDto s);
    }

    public MovieListAdapter(MovieListAdapterOnClickHandler onClick) {
        this.mClickHandler=onClick;
    }


    public class MovieListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mPosterItemImageView;

        public MovieListAdapterViewHolder(View view) {
            super(view);

            mPosterItemImageView = (ImageView) view.findViewById(R.id.movie_item_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieDto movie = mMovieListData.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public MovieListAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.movie_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        view.getLayoutParams().height = (int) (viewGroup.getWidth()/2 * 1.5f);

        return new MovieListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListAdapterViewHolder MoviesAdapterViewHolder, int position) {
        String TitleForThisMovie = mMovieListData.get(position).getTitle();
        //MoviesAdapterViewHolder.mTitleTextView.setText(TitleForThisMovie);

        Picasso.with(MoviesAdapterViewHolder.mPosterItemImageView.getContext())
                .load(Constants.BASE_URL_IMAGE+Constants.PARAM_SIZE+mMovieListData.get(position).getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(MoviesAdapterViewHolder.mPosterItemImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieListData) return 0;
        return mMovieListData.size();
    }

    public void setMovieData(ArrayList<MovieDto> movieData) {
        mMovieListData = movieData;
        notifyDataSetChanged();
    }
}
