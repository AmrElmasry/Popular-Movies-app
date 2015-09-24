package com.moviesapp.amrelmasry.popular_movies_app;

import android.content.Intent;
import android.database.Cursor;
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

    private String fetchType = "FETCH_POPULAR"; // TODO change later

    static final int COL_MOVIE_API_ID = 2;


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


                fetchMovies(pageNumber, false);
                pageNumber++;
            }
        });

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = (Cursor) parent.getItemAtPosition(position);


                Intent intent = new Intent(getActivity(), MovieDetails.class);
                String movieAPIID = cursor.getString(COL_MOVIE_API_ID);

                intent.putExtra(getString(R.string.movie_api_id), movieAPIID);

                intent.putExtra(getString(R.string.fetch_type), fetchType);
                startActivity(intent);

            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        fetchMovies(1, true);

    }


    private void fetchMovies(Integer page, boolean isInitialFetch) {
        FetchPopularMovies fetchPopularMovies = new FetchPopularMovies(getActivity(), page, isInitialFetch);
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
//        if (ConnectionUtilities.isDBReady) {
//            moviesAdapter.swapCursor(cursor);
//
//        } else {
//            Toast.makeText(getActivity(), "Not Ready, No Loader", Toast.LENGTH_SHORT).show();
//        }
        moviesAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        moviesAdapter.swapCursor(null);
    }
}
