package com.moviesapp.amrelmasry.popular_movies_app;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.moviesapp.amrelmasry.popular_movies_app.adapters.MoviesAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.provider.popular.PopularColumns;
import com.moviesapp.amrelmasry.popular_movies_app.sync.FetchPopularMovies;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.CustomScrollListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIES_LOADER = 0;
    private MoviesAdapter moviesAdapter;
    private Integer pageNumber;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView moviesGridView = (GridView) rootView.findViewById(R.id.main_girdview);

        moviesAdapter = new MoviesAdapter(getActivity(), null, 0);

        moviesGridView.setAdapter(moviesAdapter);

        pageNumber = 2;

        moviesGridView.setOnScrollListener(new CustomScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {


                fetchMovies(pageNumber);
                pageNumber++;
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        fetchMovies(1);

    }

    private void fetchMovies(Integer page) {
        FetchPopularMovies fetchPopularMovies = new FetchPopularMovies(getActivity(), page);
        fetchPopularMovies.execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIES_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), PopularColumns.CONTENT_URI, null, null, null, null);
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
