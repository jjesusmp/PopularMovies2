package com.example.android.popularmovies2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by jjesusmp
 */

public class FavoriteMoviesContentProvider extends ContentProvider{

    static final int CODE_FAVORITE_MOVIES = 100;
    static final int CODE_FAVORITE_MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private FavoriteMoviesDbHelper mFavoriteMoviesDbHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavoriteMoviesContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, FavoriteMoviesContract.PATH_FAVORITE_MOVIES, CODE_FAVORITE_MOVIES);
        uriMatcher.addURI(authority, FavoriteMoviesContract.PATH_FAVORITE_MOVIES + "/#", CODE_FAVORITE_MOVIE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mFavoriteMoviesDbHelper = new FavoriteMoviesDbHelper(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mFavoriteMoviesDbHelper.getReadableDatabase();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {

            case CODE_FAVORITE_MOVIES: {
                cursor =  db.query(FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            case CODE_FAVORITE_MOVIE_WITH_ID:

                String id = uri.getPathSegments().get(1);

                String mSelection = "idMovie=?";
                String[] mSelectionArgs = new String[]{id};

                cursor =  db.query(FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mFavoriteMoviesDbHelper.getWritableDatabase();

        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITE_MOVIES:

                long id = db.insert(FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME,null,values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(FavoriteMoviesContract.FavoriteMovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mFavoriteMoviesDbHelper.getWritableDatabase();

        int FavoriteMoviesDeleted;

        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITE_MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                FavoriteMoviesDeleted = db.delete(FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME, FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_ID_MOVIE+"=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (FavoriteMoviesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return FavoriteMoviesDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mFavoriteMoviesDbHelper.getWritableDatabase();

        int FavoriteMoviesUpdated;
        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITE_MOVIES:
                FavoriteMoviesUpdated = db.update(FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (FavoriteMoviesUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return FavoriteMoviesUpdated;
    }

}
