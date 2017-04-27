package com.example.android.popularmovies2.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jjesusmp on 21/04/2017.
 */

public class FavoriteMoviesContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static  final String PATH_FAVORITE_MOVIES = "favorite_movies";

    public static final class FavoriteMovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITE_MOVIES)
                .build();

        public static final String TABLE_NAME = "favorite_movies";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_SYPNOSIS = "sypnosis";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_ID_MOVIE = "idMovie";

    }
}
