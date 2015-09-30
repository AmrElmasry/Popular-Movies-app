package com.moviesapp.amrelmasry.popular_movies_app;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.moviesapp.amrelmasry.popular_movies_app.adapters.ReviewsAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.adapters.TrailersAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.loaders.ReviewsLoader;
import com.moviesapp.amrelmasry.popular_movies_app.loaders.TrailersLoader;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.DatabaseUtilities;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List> {

    String movieApiId;
    String tableName;
    String tableUri;

    String movieTitle;
    String movieOverview;
    String movieReleaseDate;
    String movieVoteAverage;
    String moviePosterPath;
    TrailersAdapter trailersAdapter;
    ReviewsAdapter reviewsAdapter;
    ListView trailersListView;
    ListView reviewsListView;

    private static final int TRAILERS_LOADER_ID = 1;
    private static final int REVIEWS_LOADER_ID = 2;


    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Log.i("CREATE", "FRAGMENT CREATED");

        ImageView moviePoster = (ImageView) rootView.findViewById(R.id.movie_poster);
        Button favorites = (Button) rootView.findViewById(R.id.add_to_favorites);

        TextView movieTitleTextView = (TextView) rootView.findViewById(R.id.movie_title);
        TextView moviePlotTextView = (TextView) rootView.findViewById(R.id.movie_plot);  // overview
        TextView movieRatingTextView = (TextView) rootView.findViewById(R.id.movie_rating); //vote_average
        TextView movieDateTextView = (TextView) rootView.findViewById(R.id.movie_release_date); //release date

        reviewsListView = (ListView) rootView.findViewById(R.id.reviews_list);
        trailersListView = (ListView) rootView.findViewById(R.id.trailers_list);

        trailersAdapter = new TrailersAdapter(getActivity());
        trailersListView.setAdapter(trailersAdapter);

        reviewsAdapter = new ReviewsAdapter(getActivity());
        reviewsListView.setAdapter(reviewsAdapter);

        final Bundle arguments = getArguments();


        if (arguments != null) {
            movieApiId = arguments.getString(getString(R.string.movie_api_id));
            tableName = arguments.getString(getString(R.string.table_name));
            tableUri = arguments.getString(getString(R.string.table_Uri));

        }

        Cursor movieCursor = DatabaseUtilities.getMovieFromDB(movieApiId, tableName, Uri.parse(tableUri), getActivity());

        movieTitle = movieCursor.getString(Utilities.COL_TITLE);
        movieOverview = movieCursor.getString(Utilities.COL_OVERVIEW);
        movieVoteAverage = movieCursor.getString(Utilities.COL_VOTE_AVERAGE);
        movieReleaseDate = movieCursor.getString(Utilities.COL_RELEASE_DATE);
        moviePosterPath = movieCursor.getString(Utilities.COL_POSTER_PATH);


//            // TODO WHICH IMG SIZE
        // TODO REPLACR HARD CODED URL
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + moviePosterPath).into(moviePoster);
        movieTitleTextView.setText(movieTitle);
        moviePlotTextView.setText(movieOverview);
        movieRatingTextView.setText(movieVoteAverage);
        movieDateTextView.setText(movieReleaseDate);

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert into database

                if (arguments != null) {
                    DatabaseUtilities.changeFavoriteState(movieApiId, movieTitle,
                            movieOverview, movieReleaseDate, movieVoteAverage,
                            moviePosterPath, getActivity());
                }


            }
        });


        return rootView;
    }


    public static MovieDetailsFragment newInstance(String movieApiId, String tableName, String tableUri, Context context) {
        MovieDetailsFragment f = new MovieDetailsFragment();

        Bundle arguments = new Bundle();
        arguments.putString(context.getString(R.string.movie_api_id), movieApiId);

        Log.i("INTENT", "Sent :" + movieApiId);

        arguments.putString(context.getString(R.string.table_name), tableName);
        arguments.putString(context.getString(R.string.table_Uri), tableUri);

        f.setArguments(arguments);


        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("LoaderUpdate", "Initialize Loader in onActivityCreated");

        getLoaderManager().initLoader(TRAILERS_LOADER_ID, null, this).forceLoad();
        getLoaderManager().initLoader(REVIEWS_LOADER_ID, null, this).forceLoad();

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        Log.i("LoaderUpdate", "Create Loader in onCreateLoader");

        switch (id) {
            case TRAILERS_LOADER_ID:
                return new TrailersLoader(getActivity());

            case REVIEWS_LOADER_ID:
                return new ReviewsLoader(getActivity());
        }
        return null;
    }


    @Override
    public void onLoadFinished(Loader<List> loader, List data) {
        Log.i("LoaderUpdate", "Loader Finished , start to popualte data");

        switch (loader.getId()) {
            case TRAILERS_LOADER_ID:
                trailersAdapter.addAll(data);
                Utilities.setListViewHeightBasedOnChildren(trailersListView);
                break;

            case REVIEWS_LOADER_ID:
                reviewsAdapter.addAll(data);
                Utilities.setListViewHeightBasedOnChildren(reviewsListView);
                break;
        }


    }

    @Override
    public void onLoaderReset(Loader<List> loader) {
        switch (loader.getId()) {
            case TRAILERS_LOADER_ID:
                trailersAdapter.clear();
                break;

            case REVIEWS_LOADER_ID:
                reviewsAdapter.clear();
                break;
        }


        Log.i("LoaderUpdate", "Clear Adapter");

    }


}
