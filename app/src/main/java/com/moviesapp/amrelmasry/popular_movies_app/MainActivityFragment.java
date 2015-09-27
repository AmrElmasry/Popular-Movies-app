package com.moviesapp.amrelmasry.popular_movies_app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.moviesapp.amrelmasry.popular_movies_app.adapters.MoviesAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.provider.favoritesmovies.FavoritesMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.mostratedmovies.MostRatedMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.popularmovies.PopularMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.sync.FetchPopularMovies;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.CustomScrollListener;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.Utilities;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIES_LOADER = 0;

    private MoviesAdapter moviesAdapter;
    private Integer pageNumber;
    String showMoviesBy;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView moviesGridView = (GridView) rootView.findViewById(R.id.main_girdview);

        moviesAdapter = new MoviesAdapter(getActivity(), null, 0);

        moviesGridView.setAdapter(moviesAdapter);

        pageNumber = 2;

        moviesGridView.setOnScrollListener(new CustomScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                if (!showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {

                    fetchMovies(pageNumber, false);
                    pageNumber++;
                }
            }
        });

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tableName = Utilities.getTableName(showMoviesBy, getActivity());
                Uri tableUri = Utilities.getTableUri(showMoviesBy, getActivity());

                Cursor cursor = (Cursor) parent.getItemAtPosition(position);


                if (cursor != null) {
                    String movieAPIID = cursor.getString(Utilities.COL_API_ID);
                    ((Callback) getActivity())
                            .onItemSelected(movieAPIID, tableName, tableUri);


                }


            }
        });


        return rootView;
    }


    public interface Callback {
        public void onItemSelected(String movieApiID, String tableName, Uri contentUri);

    }

    @Override
    public void onStart() {
        super.onStart();

        pageNumber = 2;

        if (!showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {
            fetchMovies(1, true);
        }


    }


    private void fetchMovies(Integer page, boolean isInitialFetch) {

        String tableName = Utilities.getTableName(showMoviesBy, getActivity());
        Uri tableUri = Utilities.getTableUri(showMoviesBy, getActivity());


        FetchPopularMovies fetchPopularMovies = new FetchPopularMovies(getActivity(), page, isInitialFetch, tableName, tableUri);
        fetchPopularMovies.execute();
    }

    void onShowByChanged(String updatedShowBy) {
        showMoviesBy = updatedShowBy;
        getLoaderManager().restartLoader(MOVIES_LOADER, null, this);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        showMoviesBy = Utilities.getShowMoviesBy(getActivity());

        getLoaderManager().initLoader(MOVIES_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        if (showMoviesBy.equals(getString(R.string.pref_sort_by_popular))) {
            return new CursorLoader(getActivity(), PopularMoviesColumns.CONTENT_URI, null, null, null, null);

        } else if (showMoviesBy.equals(getString(R.string.pref_sort_by_most_rated))) {
            return new CursorLoader(getActivity(), MostRatedMoviesColumns.CONTENT_URI, null, null, null, null);

        } else if (showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {
            return new CursorLoader(getActivity(), FavoritesMoviesColumns.CONTENT_URI, null, null, null, null);

        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        moviesAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        moviesAdapter.swapCursor(null);
    }

}
