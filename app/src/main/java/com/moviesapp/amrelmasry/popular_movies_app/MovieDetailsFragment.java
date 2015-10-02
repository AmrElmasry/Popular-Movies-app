package com.moviesapp.amrelmasry.popular_movies_app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.moviesapp.amrelmasry.popular_movies_app.adapters.ReviewsAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.adapters.TrailersAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.loaders.ReviewsLoader;
import com.moviesapp.amrelmasry.popular_movies_app.loaders.TrailersLoader;
import com.moviesapp.amrelmasry.popular_movies_app.models.Trailer;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.DatabaseUtilities;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List> {

    String movieApiId;
    String tableName;
    String tableUri;

    private String movieTitle;
    private String movieOverview;
    private String movieReleaseDate;
    private String movieVoteAverage;
    private String moviePosterPath;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private ListView trailersListView;
    private ListView reviewsListView;
    private ShareActionProvider mShareActionProvider;


    private static final int TRAILERS_LOADER_ID = 1;
    private static final int REVIEWS_LOADER_ID = 2;


    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Log.i("CREATE", "FRAGMENT CREATED");
        setHasOptionsMenu(true);

        ImageView moviePoster = (ImageView) rootView.findViewById(R.id.movie_poster);

        Button favorite = (Button) rootView.findViewById(R.id.add_to_favorites);
        Button shareTrailer = (Button) rootView.findViewById(R.id.share_trailer);

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

        }


        favorite.setOnClickListener(new View.OnClickListener() {
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

        shareTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        reviewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Clicked!", Toast.LENGTH_SHORT).show();
                Trailer trailer = (Trailer) parent.getItemAtPosition(position);
                // TODO REMOVE HARD CODED URI
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                startActivity(intent);

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

        if (movieApiId != null) {
            getLoaderManager().initLoader(TRAILERS_LOADER_ID, null, this).forceLoad();
            getLoaderManager().initLoader(REVIEWS_LOADER_ID, null, this).forceLoad();
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        Log.i("LoaderUpdate", "Create Loader in onCreateLoader");

        if (movieApiId != null) {
            switch (id) {
                case TRAILERS_LOADER_ID:
                    return new TrailersLoader(getActivity(), movieApiId);

                case REVIEWS_LOADER_ID:
                    return new ReviewsLoader(getActivity(), movieApiId);
            }
        }


        return null;
    }


    @Override
    public void onLoadFinished(Loader<List> loader, List data) {
        Log.i("LoaderUpdate", "Loader Finished , start to popualte data");

        if (data != null) {
            switch (loader.getId()) {
                case TRAILERS_LOADER_ID:
                    trailersAdapter.addAll(data);
                    Utilities.setListViewHeightBasedOnChildren(trailersListView);

                    // If onCreateOptionsMenu has already happened, we need to update the share intent now.
                    if (mShareActionProvider != null) {
                        shareTrailer();
                    }
                    break;

                case REVIEWS_LOADER_ID:
                    // TODO HANDLE NO REVIEWS CASE AND NO CONNECTION CASE
                    if (data.size() > 0) {
                        reviewsAdapter.addAll(data);
                        Utilities.setListViewHeightBasedOnChildren(reviewsListView);
                    }
                    break;
            }
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

    // share trailer button :


    private void shareTrailer() {
        if (trailersAdapter.getCount() > 0) {

            Trailer firstTrailer = trailersAdapter.getItem(0);
            if (firstTrailer != null) {
//                Utilities.createShareIntent(getActivity(), "http://www.youtube.com/watch?v=" + firstTrailer.getKey());

                mShareActionProvider.setShareIntent(
                        Utilities.createShareIntent("http://www.youtube.com/watch?v=" + firstTrailer.getKey()));

            }
        }
    }

    ;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_movie_details, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.

        shareTrailer();
    }


}

