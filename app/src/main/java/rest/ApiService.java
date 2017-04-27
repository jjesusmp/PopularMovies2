package rest;

import com.example.android.popularmovies2.model.ReviewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jjesusmp on 27/04/2017.
 */

public interface ApiInterface {

    /*@GET("3/movie/{id}/videos")
    Call<Trailers> findTrailersById(@Path("id") long movieId, @Query("api_key") String apiKey);*/

    @GET("/movie/{id}/reviews")
    Call<ReviewsResponse> findReviewsById(@Path("id") String movieId, @Query("api_key") String apiKey);

   /* @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/
}

