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
    private int pageNumber;
    private String showMoviesBy;
    private RecyclerView moviesRecyclerView;

    private int mPosition = RecyclerView.NO_POSITION;
    private static final String LAST_SELECTED_POSITION = "last_selected_position";
    private static final String Last_PAGE_NUMBER_KEY = "last_page_number_used";
    private static final String Last_SHOW_BY_KEY = "last_show_b";


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





//


        return rootView;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("ROTATE", "Device rotated");
        super.onSaveInstanceState(outState);

        outState.putInt(Last_PAGE_NUMBER_KEY, pageNumber);
        outState.putString(Last_SHOW_BY_KEY, showMoviesBy);

        if (mPosition != RecyclerView.NO_POSITION) {
            outState.putInt(LAST_SELECTED_POSITION, mPosition);
            Log.i("Really", "Save last Position " + mPosition);

        }

        Log.i("Really", "Save last page " + pageNumber);

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
    public void onStart() {
        super.onStart();

        Log.i("Finally", "on start reached");


    }

    @Override
    public void onResume() {

        super.onResume();

        getLoaderManager().initLoader(MOVIES_LOADER, null, this);


        Log.i("First_Load", "on start - page number =  " + pageNumber);


        if (mPosition != RecyclerView.NO_POSITION) {
//            moviesRecyclerView.scrollToPosition(mPosition);
            moviesRecyclerView.getLayoutManager().scrollToPosition(mPosition);
            Log.i("Really", "Scrolled to position" + mPosition);

        }
    }


    private void fetchMovies(int page, boolean isInitialFetch) {

        String tableName = DatabaseUtilities.getTableName(showMoviesBy, getActivity());
        Uri tableUri = DatabaseUtilities.getTableUri(showMoviesBy, getActivity());

        Log.i("Really", "Fetching page" + page + "From table : " + tableName);


        FetchMoviesTask FetchMoviesTask = new FetchMoviesTask(getActivity(), page, isInitialFetch, tableName, tableUri);
        FetchMoviesTask.execute();
    }

    void onShowByChanged(String updatedShowBy) {

        Log.i("First_Load", "show by changed from activity - page number is   " + pageNumber);

        showMoviesBy = updatedShowBy;
        mPosition = RecyclerView.NO_POSITION;
        Log.i("Really", "Show BY CHANGED - INITIAL SET UP");

        initialSetup();

        getLoaderManager().restartLoader(MOVIES_LOADER, null, this);

    }

    private void initialSetup() {
        if (!showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {

            fetchMovies(1, true);
            pageNumber = 2;
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey(Last_PAGE_NUMBER_KEY)) {
                pageNumber = savedInstanceState.getInt(Last_PAGE_NUMBER_KEY);
                Log.i("Really", "Load last page = " + pageNumber);


            }
            if (savedInstanceState.containsKey(LAST_SELECTED_POSITION)) {
                mPosition = savedInstanceState.getInt(LAST_SELECTED_POSITION);
                Log.i("Really", "Load last position = " + mPosition);

            }

//            if (savedInstanceState.containsKey(Last_SHOW_BY_KEY)) {
//                String lastShowBy = savedInstanceState.getString(Last_SHOW_BY_KEY);
//                if (!showMoviesBy.equals(lastShowBy)) {
//                    Log.i("Really", "show by changed from state - ");
//                    onShowByChanged(GeneralUtilities.getShowMoviesBy(getActivity()));
//                }
//            }


        }

        Log.i("Finally", "Activity created");

        showMoviesBy = GeneralUtilities.getShowMoviesBy(getActivity());

        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState == null) {

            initialSetup();

        }
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

        Log.i("Finally", "Load finished");

        if (!showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {
            moviesAdapter.swapCursor(cursor);
            endlessRecyclerViewAdapter.onDataReady(true);
        } else {
            moviesAdapter.swapCursor(cursor);
            endlessRecyclerViewAdapter.onDataReady(false);
        }

//        if (mPosition != RecyclerView.NO_POSITION) {
////            moviesRecyclerView.scrollToPosition(mPosition);
//            moviesRecyclerView.getLayoutManager().scrollToPosition(mPosition);
//        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        moviesAdapter.swapCursor(null);
    }

}
