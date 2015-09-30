package com.moviesapp.amrelmasry.popular_movies_app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moviesapp.amrelmasry.popular_movies_app.adapters.MoviesRecyclerAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.adapters.MoviesRecyclerAdapter.SimpleViewHolder.ViewHolderClicksListener;
import com.moviesapp.amrelmasry.popular_movies_app.provider.favoritesmovies.FavoritesMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.mostratedmovies.MostRatedMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.popularmovies.PopularMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.sync.FetchPopularMovies;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.Utilities;
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, EndlessRecyclerViewAdapter.RequestToLoadMoreListener {

    private static final int MOVIES_LOADER = 0;

    private MoviesRecyclerAdapter moviesAdapter;
    EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
    private Integer pageNumber;
    String showMoviesBy;

    public MainActivityFragment() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final RecyclerView moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.movies_recycler_view);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        moviesRecyclerView.setLayoutManager(manager);
        moviesRecyclerView.setHasFixedSize(true);

        ViewHolderClicksListener clicksListener =
                new MoviesRecyclerAdapter.SimpleViewHolder.ViewHolderClicksListener() {


                    @Override
                    public void onMovieClick(View view, int position) {

//                        Toast.makeText(getActivity(), "Movie API ID IS : " + moviesAdapter.getMovieApiID(position), Toast.LENGTH_SHORT).show();
                        Log.i("MOVIE_CILICK", "position b " + position);

                        String movieApiID = moviesAdapter.getMovieApiID(position);
                        Log.i("MOVIE_CILICK", "api id b " + movieApiID);


                        String tableName = Utilities.getTableName(showMoviesBy, getActivity());
                        Uri tableUri = Utilities.getTableUri(showMoviesBy, getActivity());
                        ((Callback) getActivity())
                                .onItemSelected(movieApiID, tableName, tableUri);

                    }
                };
        moviesAdapter = new MoviesRecyclerAdapter(R.layout.movie_item, null, getActivity(), clicksListener);
        endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(getActivity(), moviesAdapter, this);
        moviesRecyclerView.setAdapter(endlessRecyclerViewAdapter);


        pageNumber = 2;


//


        return rootView;
    }

    @Override
    public void onLoadMoreRequested() {

        Log.i("LOADING", "Loading page number : " + pageNumber);

        if (!showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {

            // scroll to load more
            fetchMovies(pageNumber, false);
            pageNumber++;
            Toast.makeText(getActivity(), "Loading More", Toast.LENGTH_SHORT).show();

        }
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

        if (!showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {
            moviesAdapter.swapCursor(cursor);
            endlessRecyclerViewAdapter.onDataReady(true);
        } else {
            moviesAdapter.swapCursor(cursor);
            endlessRecyclerViewAdapter.onDataReady(false);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        moviesAdapter.swapCursor(null);
    }

}
