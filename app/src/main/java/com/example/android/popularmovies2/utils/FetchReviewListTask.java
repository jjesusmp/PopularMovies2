package com.example.android.popularmovies2.utils;

import android.os.AsyncTask;

import com.example.android.popularmovies2.model.ReviewDto;
import com.example.android.popularmovies2.model.ReviewsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.android.popularmovies2.rest.ApiService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jjesusmp on 27/04/2017.
 */

public class FetchReviewListTask extends AsyncTask<String, Void, List<ReviewDto>> {

       private final Listener mListener;
    /**
     * Interface definition for a callback to be invoked when reviews are loaded.
     */
    public interface Listener {
        void onReviewsFetchFinished(List<ReviewDto> reviews);
    }

    public FetchReviewListTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<ReviewDto> doInBackground(String... params) {
        String id = params[0];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ReviewsResponse> call = apiService.findReviewsById(id, Constants.API_KEY);
        Response<ReviewsResponse> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReviewsResponse reviews = response.body();
        return reviews.getResults();
    }

    @Override
    protected void onPostExecute(List<ReviewDto> listReviewData) {
        if (listReviewData != null) {
            mListener.onReviewsFetchFinished(listReviewData);
        } else {
            mListener.onReviewsFetchFinished(new ArrayList<ReviewDto>());
        }
    }
}