package com.example.android.popularmovies2.utils;

import android.os.AsyncTask;

import com.example.android.popularmovies2.model.ReviewDto;
import com.example.android.popularmovies2.model.ReviewsResponse;
import com.example.android.popularmovies2.model.TrailerDto;
import com.example.android.popularmovies2.model.TrailersResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rest.ApiService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jjesusmp
 */

public class FetchTrailerListTask extends AsyncTask<String, Void, List<TrailerDto>> {

    private final Listener mListener;
    /**
     * Interface definition for a callback to be invoked when reviews are loaded.
     */
    public interface Listener {
        void onTrailersFetchFinished(List<TrailerDto> trailers);
    }

    public FetchTrailerListTask(FetchTrailerListTask.Listener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<TrailerDto> doInBackground(String... params) {
        String id = params[0];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<TrailersResponse> call = apiService.findTrailersById(id, Constants.API_KEY);
        Response<TrailersResponse> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TrailersResponse reviews = response.body();
        return reviews.getResults();
    }

    @Override
    protected void onPostExecute(List<TrailerDto> listTrailerData) {
        if (listTrailerData != null) {
            mListener.onTrailersFetchFinished(listTrailerData);
        } else {
            mListener.onTrailersFetchFinished(new ArrayList<TrailerDto>());
        }
    }
}