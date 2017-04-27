package com.example.android.popularmovies2.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.adapter.MovieListAdapter;
import com.example.android.popularmovies2.data.FavoriteMoviesContract;
import com.example.android.popularmovies2.model.MovieDto;
import com.example.android.popularmovies2.utils.Constants;
import com.example.android.popularmovies2.utils.PopularMoviesUtils;
import com.example.android.popularmovies2.utils.NetworkUtils;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieListAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView mRecyclerView;
    private MovieListAdapter mMovieListAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int GET_MOVIE_LIST_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.movie_list);
        mErrorMessageDisplay = (TextView) findViewById(R.id.ml_error_message_display);

        LinearLayoutManager layoutManager
                = new GridLayoutManager(this,2);//2 columns
        mRecyclerView.setLayoutManager(layoutManager);
        mMovieListAdapter = new MovieListAdapter(this);
        mRecyclerView.setAdapter(mMovieListAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        loadMovieListData(Constants.orderByPopular);//orderByPopular is the default order
    }

    private void loadMovieListData(String sortBy) {
        showMovieListDataView();
        new FetchMovieListTask().execute(sortBy);
    }

    @Override
    public void onClick(MovieDto movieInfoForThisItem) {
        Context context = this;
        Toast.makeText(context, movieInfoForThisItem.getTitle(), Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(this,MovieDetailsActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("MovieDto", movieInfoForThisItem);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void showMovieListDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuSortRating) {
            mMovieListAdapter.setMovieData(null);
            loadMovieListData(Constants.orderByTopRated);
            return true;
        } if (id == R.id.menuSortPopular) {
            mMovieListAdapter.setMovieData(null);
            loadMovieListData(Constants.orderByPopular);
            return true;
        } if (id == R.id.menuSortFavorites) {//get from local db
            getSupportLoaderManager().restartLoader(GET_MOVIE_LIST_LOADER_ID, null, this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchMovieListTask extends AsyncTask<String, Void, ArrayList<MovieDto>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MovieDto> doInBackground(String... params) {

            String orderBy = params[0];
            URL url = NetworkUtils.buildAPIUrl(orderBy);

            try {
                String jsonMovieListResponse = NetworkUtils
                        .getResponseFromHttpUrl(url);

                ArrayList<MovieDto> simpleMovieListData = PopularMoviesUtils.getMovieListFromJson(jsonMovieListResponse);

                return simpleMovieListData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieDto> listMovieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (listMovieData != null) {
                showMovieListDataView();
                mMovieListAdapter.setMovieData(listMovieData);
            } else {
                showErrorMessage();
            }
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mFavoriteMoviesData = null;
            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mFavoriteMoviesData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mFavoriteMoviesData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }
            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data
                // Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data
                try {
                    return getContentResolver().query(FavoriteMoviesContract.FavoriteMovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }
            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mFavoriteMoviesData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolders
        try {
            mMovieListAdapter.setMovieData(PopularMoviesUtils.getMovieDtoListFromCursor(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieListAdapter.setMovieData(null);
    }
}