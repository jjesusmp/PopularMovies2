package com.example.android.popularmovies2.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewsResponse {


        @SerializedName("results")
        @Expose
        private List<ReviewDto> results = new ArrayList<>();

        public List<ReviewDto> getResults() {
            return results;
        }

        public void setResults(List<ReviewDto> results) {
            this.results = results;
        }



    }

