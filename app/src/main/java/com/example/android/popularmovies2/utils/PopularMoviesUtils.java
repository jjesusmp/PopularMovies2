package com.example.android.popularmovies2.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;

import com.example.android.popularmovies2.data.FavoriteMoviesContract;
import com.example.android.popularmovies2.model.MovieDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;


/**
 * Created by jjesusmp
 */

public final class PopularMoviesUtils {

    private static final String TITLE = "title";
    private static final String IMAGE = "poster_path";
    private static final String SYPNOSIS = "overview";
    private static final String USER_RATING = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String MESSAGE_CODE = "cod"; //error code
    private static final String _EXT_ID = "id"; //API´s ID

    public static ArrayList<MovieDto> getMovieListFromJson(String MoviesListJsonResponseStr)
                throws JSONException {

            ArrayList<MovieDto> parsedMovieList = new ArrayList<MovieDto>();

            JSONObject ResultsJson = new JSONObject(MoviesListJsonResponseStr);

            /* Is there an error? */
            if (ResultsJson.has(MESSAGE_CODE)) {
                int errorCode = ResultsJson.getInt(MESSAGE_CODE);

                switch (errorCode) {
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                        return null;
                    default:
                    /* Server probably down */
                        return null;
                }
            }
            JSONArray moviesData = ResultsJson.getJSONArray("results");

            for (int i=0; i<moviesData.length(); i++) {
                MovieDto movie = new MovieDto();
                JSONObject movieInfo = moviesData.getJSONObject(i);
                movie.setTitle(movieInfo.getString(TITLE));
                movie.setImage(movieInfo.getString(IMAGE));
                movie.setSypnosis(movieInfo.getString(SYPNOSIS));
                movie.setUser_rating(movieInfo.getString(USER_RATING));
                movie.setRelease_date(movieInfo.getString(RELEASE_DATE));
                movie.set__idMovie(movieInfo.getString(_EXT_ID));
                parsedMovieList.add(movie);
            }
            Log.d("TAG",parsedMovieList.toString());

            return parsedMovieList;
        }

    public static ArrayList<MovieDto> getMovieDtoListFromCursor(Cursor mCursor)
            throws JSONException {

        ArrayList<MovieDto> parsedMovieList = new ArrayList<MovieDto>();

        int idIndex = mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_ID_MOVIE);
        int titleIndex = mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_TITLE);
        int imageIndex = mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_IMAGE);
        int sypnosisIndex = mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_SYPNOSIS);
        int user_ratingIndex = mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_USER_RATING);
        int release_dateIndex = mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE);


        if (mCursor != null)
        {
            if (mCursor.moveToFirst()) {
                for(int i = 0; i < mCursor.getCount(); i ++){

                    MovieDto mMovie = new MovieDto();

                    mMovie.set__idMovie(mCursor.getString(idIndex));
                    mMovie.setTitle(mCursor.getString(titleIndex));
                    mMovie.setImage(mCursor.getString(imageIndex));
                    mMovie.setSypnosis(mCursor.getString(sypnosisIndex));
                    mMovie.setUser_rating(mCursor.getString(user_ratingIndex));
                    mMovie.setRelease_date(mCursor.getString(release_dateIndex));

                    parsedMovieList.add(mMovie);

                    mCursor.moveToNext();
                }
            }
            mCursor.close();
        }

        return parsedMovieList;
    }

    }

