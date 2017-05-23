package com.example.android.popularmovies2.utils;

import android.os.AsyncTask;

import com.example.android.popularmovies2.model.MovieDto;
import com.example.android.popularmovies2.model.MoviesResponse;
import com.example.android.popularmovies2.rest.ApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by josejesus.munozpujaz on 23/05/2017.
 */

public class FetchMovieListTask extends AsyncTask<String, Void, List<MovieDto>> {

    private final Listener mListener;
    /**
     * Interface definition for a callback to be invoked when reviews are loaded.
     */
    public interface Listener {
        void onMoviesFetchFinished(List<MovieDto> reviews);
    }

    public FetchMovieListTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<MovieDto> doInBackground(String... params) {
        String orderBy = params[0];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<MoviesResponse> call;
        if(orderBy.equals(Constants.orderByFavorites)){
            call = apiService.getTopRatedMovies(Constants.API_KEY);
        }else{
            call = apiService.getPopularMovies(Constants.API_KEY);
        }
        Response<MoviesResponse> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MoviesResponse movies = response.body();
        return movies.getResults();
    }

    @Override
    protected void onPostExecute(List<MovieDto> listMovieData) {
        if (listMovieData != null) {
            mListener.onMoviesFetchFinished(listMovieData);
        } else {
            mListener.onMoviesFetchFinished(new ArrayList<MovieDto>());
        }
    }
}