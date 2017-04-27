package com.example.android.popularmovies2.network;

import java.util.List;

import com.example.android.popularmovies2.model.ReviewDto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewsListResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<ReviewDto> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ReviewDto> getResults() {
        return results;
    }

    public void setResults(List<ReviewDto> results) {
        this.results = results;
    }

}
