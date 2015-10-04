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
import com.moviesapp.amrelmasry.popular_movies_app.provider.helper.MoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.sync.FetchMoviesTask;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.DatabaseUtilities;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.GeneralUtilities;
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, EndlessRecyclerViewAdapter.RequestToLoadMoreListener {

    private static final int MOVIES_LOADER = 0;

    private MoviesRecyclerAdapter moviesAdapter;
    private EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
    private Integer pageNumber;
    private String showMoviesBy;
    private RecyclerView moviesRecyclerView;

    private int mPosition = RecyclerView.NO_POSITION;
    private static final String SELECTED_KEY = "last_selected_position";


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.movies_recycler_view);

        int spanCount = getActivity().getResources().getInteger(R.integer.grid_layout_span_count);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), spanCount);
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


                        Uri tableUri = DatabaseUtilities.getTableUri(showMoviesBy, getActivity());
                        ((Callback) getActivity())
                                .onItemSelected(movieApiID, tableUri);
                        mPosition = position;

                    }
                };
        moviesAdapter = new MoviesRecyclerAdapter(R.layout.movie_item, null, getActivity(), clicksListener);
        endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(getActivity(), moviesAdapter, this);
        moviesRecyclerView.setAdapter(endlessRecyclerViewAdapter);


        pageNumber = 2;

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
//


        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("ROTATE", "Device rotated");

        if (mPosition != RecyclerView.NO_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
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
        public void onItemSelected(String movieApiID, Uri contentUri);

    }

    @Override
    public void onResume() {
        super.onResume();
        pageNumber = 2;

        Log.i("First_Load", "on start - page number =  " + pageNumber);

        if (!showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {

            Log.i("First_Load", "on start - not favorites - get page 1  ");

            fetchMovies(1, true);
        }
    }


    private void fetchMovies(Integer page, boolean isInitialFetch) {

        String tableName = DatabaseUtilities.getTableName(showMoviesBy, getActivity());
        Uri tableUri = DatabaseUtilities.getTableUri(showMoviesBy, getActivity());

        Log.i("First_Load", "Fetching page" + page + "From table : " + tableName);


        FetchMoviesTask FetchMoviesTask = new FetchMoviesTask(getActivity(), page, isInitialFetch, tableName, tableUri);
        FetchMoviesTask.execute();
    }

    void onShowByChanged(String updatedShowBy) {

        Log.i("First_Load", "show by changed - page number is   " + pageNumber);

        showMoviesBy = updatedShowBy;

        if (!showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {

            fetchMovies(1, true);
        }

        getLoaderManager().restartLoader(MOVIES_LOADER, null, this);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        showMoviesBy = GeneralUtilities.getShowMoviesBy(getActivity());

        getLoaderManager().initLoader(MOVIES_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        if (showMoviesBy.equals(getString(R.string.pref_sort_by_popular))) {
            return new CursorLoader(getActivity(), MoviesColumns.POPULAR_CONTENT_URI, null, null, null, null);

        } else if (showMoviesBy.equals(getString(R.string.pref_sort_by_most_rated))) {
            return new CursorLoader(getActivity(), MoviesColumns.MOST_RATED_CONTENT_URI, null, null, null, null);

        } else if (showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {
            return new CursorLoader(getActivity(), MoviesColumns.FAVORITES_CONTENT_URI, null, null, null, null);

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

        if (mPosition != RecyclerView.NO_POSITION) {
//            moviesRecyclerView.scrollToPosition(mPosition);
            moviesRecyclerView.getLayoutManager().scrollToPosition(mPosition);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        moviesAdapter.swapCursor(null);
    }

}
