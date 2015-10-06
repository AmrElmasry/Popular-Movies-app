package com.moviesapp.amrelmasry.popular_movies_app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private FloatingActionButton scrollToTop;

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

        scrollToTop = (FloatingActionButton) rootView.findViewById(R.id.scroll_to_top);

        moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.movies_recycler_view);

        int spanCount = getActivity().getResources().getInteger(R.integer.grid_layout_span_count);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), spanCount);
        moviesRecyclerView.setLayoutManager(manager);
        moviesRecyclerView.setHasFixedSize(true);

        ViewHolderClicksListener clicksListener =
                new MoviesRecyclerAdapter.SimpleViewHolder.ViewHolderClicksListener() {


                    @Override
                    public void onMovieClick(View view, int position) {


                        String movieApiID = moviesAdapter.getMovieApiID(position);


                        Uri tableUri = DatabaseUtilities.getTableUri(showMoviesBy, getActivity());
                        ((Callback) getActivity())
                                .onItemSelected(movieApiID, tableUri);

                        mPosition = position;

                    }
                };
        moviesAdapter = new MoviesRecyclerAdapter(null, getActivity(), clicksListener);
        endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(getActivity(), moviesAdapter, this);
        moviesRecyclerView.setAdapter(endlessRecyclerViewAdapter);


        if (scrollToTop != null) {
            // it's a phone layout , set the scroll listener
            scrollToTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moviesRecyclerView.smoothScrollToPosition(1);
                    scrollToTop.hide();
                }
            });
        }


        return rootView;
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(Last_PAGE_NUMBER_KEY, pageNumber);
        outState.putString(Last_SHOW_BY_KEY, showMoviesBy);

        if (mPosition != RecyclerView.NO_POSITION) {
            outState.putInt(LAST_SELECTED_POSITION, mPosition);

        }


    }

    @Override
    public void onLoadMoreRequested() {


        if (!showMoviesBy.equals(getString(R.string.pref_sort_by_favorites))) {


            // load more on scrolling
            fetchMovies(pageNumber, false);
            pageNumber++;

        }
    }


    public interface Callback {
        public void onItemSelected(String movieApiID, Uri contentUri);


    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {

        super.onResume();

        getLoaderManager().initLoader(MOVIES_LOADER, null, this);


        if (mPosition != RecyclerView.NO_POSITION) {
            moviesRecyclerView.getLayoutManager().scrollToPosition(mPosition);

        }
    }


    private void fetchMovies(int page, boolean isInitialFetch) {

        String tableName = DatabaseUtilities.getTableName(showMoviesBy, getActivity());
        Uri tableUri = DatabaseUtilities.getTableUri(showMoviesBy, getActivity());


        FetchMoviesTask FetchMoviesTask = new FetchMoviesTask(getActivity(), page, isInitialFetch, tableName, tableUri);
        FetchMoviesTask.execute();
    }

    void onShowByChanged(String updatedShowBy) {


        showMoviesBy = updatedShowBy;
        mPosition = RecyclerView.NO_POSITION;

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


            }
            if (savedInstanceState.containsKey(LAST_SELECTED_POSITION)) {
                mPosition = savedInstanceState.getInt(LAST_SELECTED_POSITION);

            }


        }


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
