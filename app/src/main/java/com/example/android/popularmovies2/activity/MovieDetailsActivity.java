package com.example.android.popularmovies2.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.adapter.ReviewListAdapter;
import com.example.android.popularmovies2.data.FavoriteMoviesContract;
import com.example.android.popularmovies2.model.MovieDto;
import com.example.android.popularmovies2.model.ReviewDto;
import com.example.android.popularmovies2.model.ReviewsResponse;
import com.example.android.popularmovies2.utils.Constants;
import com.example.android.popularmovies2.utils.FetchReviewListTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rest.ApiService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity implements FetchReviewListTask.Listener,ReviewListAdapter.Callbacks{

    @BindView(R.id.abstractTitleTextView) TextView mTitle;
    @BindView(R.id.abstractPosterImageView) ImageView mImage;
    @BindView(R.id.abstractSypnosisTextView) TextView mSypnosis;
    @BindView(R.id.abstractUserRatingTextView) TextView mUserRating;
    @BindView(R.id.abstractReleaseDateTextView) TextView mReleaseDate;
    @BindView(R.id.btnAdFav) TextView mAddFavButton;
    @BindView(R.id.btnDelFav) TextView mAddDelButton;
    @BindView(R.id.review_list) RecyclerView mRecyclerView;

    private ReviewListAdapter mReviewListAdapter;
    private MovieDto mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        if(intent.hasExtra("MovieDto")) {
            mMovie = intent.getParcelableExtra("MovieDto");

            mTitle.setText(mMovie.getTitle());
            mSypnosis.setText(mMovie.getSypnosis());
            Picasso.with(mImage.getContext())
                    .load(Constants.BASE_URL_IMAGE + Constants.PARAM_SIZE + mMovie.getImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(mImage);
            mUserRating.setText(mMovie.getUser_rating());
            mReleaseDate.setText(mMovie.getRelease_date());


            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            mReviewListAdapter = new ReviewListAdapter(new ArrayList<ReviewDto>(),this);
            mRecyclerView.setAdapter(mReviewListAdapter);
            mRecyclerView.setNestedScrollingEnabled(false);
            loadReviewsListData();
        }
        ButterKnife.bind(this);

    }

    public void onClickAddFavorite(View view) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_TITLE, mMovie.getTitle());
            contentValues.put(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_IMAGE, mMovie.getImage());
            contentValues.put(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_SYPNOSIS, mMovie.getSypnosis());
            contentValues.put(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_USER_RATING, mMovie.getUser_rating());
            contentValues.put(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE, mMovie.getRelease_date());
            contentValues.put(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_ID_MOVIE, mMovie.get__idMovie());
            // Insert the content values via a ContentResolver
            Uri uri = getContentResolver().insert(FavoriteMoviesContract.FavoriteMovieEntry.CONTENT_URI,contentValues);
            // Display the URI that's returned with a Toast
            // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
            if (uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }
    }

    public void onClickDeleteFavorite (View view){

        String id = mMovie.get__idMovie();
        Uri uri = FavoriteMoviesContract.FavoriteMovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();
        getContentResolver().delete(uri, null, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void loadReviewsListData() {
        FetchReviewListTask task = new FetchReviewListTask(this);
        task.execute(mMovie.get__idMovie());
    }

    @Override
    public void onReviewsFetchFinished(List<ReviewDto> reviews) {
        mReviewListAdapter.setReviewsData(reviews);
    }

    @Override
    public void readReview(ReviewDto review, int position) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(review.getUrl())));
    }
}
