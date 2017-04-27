package com.example.android.popularmovies2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjesusmp
 */

public class TrailersResponse {

    @SerializedName("results")
    @Expose
    private List<TrailerDto> results = new ArrayList<>();

    public List<TrailerDto> getResults() {
        return results;
    }

    public void setResults(List<TrailerDto> results) {
        this.results = results;
    }
}
