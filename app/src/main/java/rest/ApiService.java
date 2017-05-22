package rest;

import com.example.android.popularmovies2.model.ReviewsResponse;
import com.example.android.popularmovies2.model.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jjesusmp on 27/04/2017.
 */

public interface ApiService {

    @GET("movie/{id}/videos")
    Call<TrailersResponse> findTrailersById(@Path("id") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewsResponse> findReviewsById(@Path("id") String movieId, @Query("api_key") String apiKey);

   /* @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/
}

