package com.example.android.popularmovies2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jjesusmp on 21/04/2017.
 */

public class FavoriteMoviesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorite_movies.db";
    private static final int DATABASE_VERSION = 1;

    public FavoriteMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_MOVIES_TABLE =
                "CREATE TABLE " + FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME + " (" +
                        FavoriteMoviesContract.FavoriteMovieEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_ID_MOVIE + " TEXT NOT NULL," +
                        FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_TITLE       + " TEXT NOT NULL, "                 +
                        FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_IMAGE + " TEXT NOT NULL,"                  +
                        FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_SYPNOSIS   + " TEXT NOT NULL, "                    +
                        FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_USER_RATING   + " TEXT NOT NULL, "                    +
                        FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE   + " TEXT NOT NULL, "+
                        " UNIQUE (" + FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_ID_MOVIE + ") ON CONFLICT REPLACE);";
        db.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
