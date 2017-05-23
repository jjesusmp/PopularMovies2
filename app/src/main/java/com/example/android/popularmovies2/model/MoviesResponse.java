package com.example.android.popularmovies2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josejesus.munozpujaz on 23/05/2017.
 */

public class MoviesResponse {

    @SerializedName("results")
    @Expose
    private List<MovieDto> results = new ArrayList<>();

    public List<MovieDto> getResults() {
        return results;
    }

    public void setResults(List<MovieDto> results) {
        this.results = results;
    }
}


