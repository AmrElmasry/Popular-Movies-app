package com.moviesapp.amrelmasry.popular_movies_app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.moviesapp.amrelmasry.popular_movies_app.adapters.ReviewsAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.adapters.TrailersAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.loaders.ReviewsLoader;
import com.moviesapp.amrelmasry.popular_movies_app.loaders.TrailersLoader;
import com.moviesapp.amrelmasry.popular_movies_app.models.Trailer;
import com.moviesapp.amrelmasry.popular_movies_app.provider.favoritesmovies.FavoritesMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.DatabaseUtilities;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.GeneralUtilities;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List> {

    private String movieApiId;
    private String tableName;
    private String tableUri;

    private TextView noReviews;
    private TextView noTrailers;
    private TextView cannotLoadTrailers;
    private TextView cannotLoadReviews;

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

    private boolean isFavoriteMovie;

    private FloatingActionButton fab;
    private static final int TRAILERS_LOADER_ID = 1;
    private static final int REVIEWS_LOADER_ID = 2;


    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Log.i("CREATE", "FRAGMENT CREATED");
        setHasOptionsMenu(true);

        ImageView moviePoster = (ImageView) rootView.findViewById(R.id.movie_poster);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        TextView movieTitleTextView = (TextView) rootView.findViewById(R.id.movie_title);
        TextView moviePlotTextView = (TextView) rootView.findViewById(R.id.movie_plot);  // overview
        TextView movieRatingTextView = (TextView) rootView.findViewById(R.id.movie_rating); //vote_average
        TextView movieDateTextView = (TextView) rootView.findViewById(R.id.movie_release_date); //release date

        noReviews = (TextView) rootView.findViewById(R.id.no_reviews);
        cannotLoadReviews = (TextView) rootView.findViewById(R.id.cannot_load_reviews);
        noTrailers = (TextView) rootView.findViewById(R.id.no_trailers);
        cannotLoadTrailers = (TextView) rootView.findViewById(R.id.cannot_load_trailers);

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

            movieTitle = movieCursor.getString(DatabaseUtilities.COL_TITLE);
            movieOverview = movieCursor.getString(DatabaseUtilities.COL_OVERVIEW);
            movieVoteAverage = movieCursor.getString(DatabaseUtilities.COL_VOTE_AVERAGE);
            movieReleaseDate = movieCursor.getString(DatabaseUtilities.COL_RELEASE_DATE);
            moviePosterPath = movieCursor.getString(DatabaseUtilities.COL_POSTER_PATH);


//            // TODO WHICH IMG SIZE
            // TODO REPLACR HARD CODED URL
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + moviePosterPath).into(moviePoster);
            movieTitleTextView.setText(movieTitle);
            moviePlotTextView.setText(movieOverview);
            movieRatingTextView.setText(movieVoteAverage);
            movieDateTextView.setText(movieReleaseDate);

        }

        if (tableUri != null && tableUri.equals(FavoritesMoviesColumns.CONTENT_URI.toString())) {
            // favorite movie
            isFavoriteMovie = true;
        } else {

            if (DatabaseUtilities.isFavoriteMovie(movieApiId, getActivity())) {
                isFavoriteMovie = true;
            } else {
                isFavoriteMovie = false;
            }
        }
        changeFavoriteButtonState();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //TODO
                if (isFavoriteMovie) {
                    DatabaseUtilities.removeFromDatabase(movieApiId,
                            FavoritesMoviesColumns.TABLE_NAME, FavoritesMoviesColumns.CONTENT_URI, getActivity());
                    isFavoriteMovie = false;
                    changeFavoriteButtonState();
                } else {
                    DatabaseUtilities.insertIntoDatabase(movieApiId, movieTitle,
                            movieOverview, movieReleaseDate, movieVoteAverage,
                            moviePosterPath, FavoritesMoviesColumns.CONTENT_URI, getActivity());
                    isFavoriteMovie = true;
                    changeFavoriteButtonState();
                }


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

    private void changeFavoriteButtonState() {
//        Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.favorite);
////        fab.setAnimation(rotate);
//
//        RotateAnimation a = new RotateAnimation(0, 90);
//        a.setFillAfter(true);
//        a.setDuration(0);
//        fab.startAnimation(a);
//
//        fab.startAnimation(rotate);
        if (isFavoriteMovie) {
            fab.setImageResource(R.drawable.ic_heart_red_filled);
        } else {
            fab.setImageResource(R.drawable.ic_heart_outline_red);

        }
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

        switch (loader.getId()) {
            case TRAILERS_LOADER_ID:

                if (data != null) {
                    if (data.size() > 0) {
                        cannotLoadTrailers.setVisibility(View.GONE);
                        noTrailers.setVisibility(View.GONE);
                        trailersAdapter.clear();
                        trailersAdapter.addAll(data);
                        GeneralUtilities.setListViewHeightBasedOnChildren(trailersListView);
                        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
                    } else {
                        // no trailers
                        noTrailers.setVisibility(View.VISIBLE);

                    }

                } else {
                    // no internet

                    Log.i("SHARE", "Null data - clear traileradpater - action provider=null");
                    cannotLoadTrailers.setVisibility(View.VISIBLE);
                    trailersAdapter.clear();
                }

                if (mShareActionProvider != null) {

                    shareTrailer();
                }


                break;

            case REVIEWS_LOADER_ID:
                if (data != null) {
                    if (data.size() > 0) {
                        cannotLoadReviews.setVisibility(View.GONE);
                        noReviews.setVisibility(View.GONE);
                        reviewsAdapter.clear();
                        reviewsAdapter.addAll(data);
                        GeneralUtilities.setListViewHeightBasedOnChildren(reviewsListView);
                    } else {
                        // no reviews
                        noReviews.setVisibility(View.VISIBLE);
                    }
                } else {
                    // no internet
                    reviewsAdapter.clear();
                    cannotLoadReviews.setVisibility(View.VISIBLE);

                }

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

    private void shareTrailer() {
        if (trailersAdapter != null && trailersAdapter.getCount() > 0) {

            Log.i("SHARE", "Update action provider - adapter count > 0");

            Trailer firstTrailer = trailersAdapter.getItem(0);
            if (firstTrailer != null) {
//                GeneralUtilities.createShareIntent(getActivity(), "http://www.youtube.com/watch?v=" + firstTrailer.getKey());
                Log.i("SHARE", "Trailer 1 exists , update my action provider");
                mShareActionProvider.setShareIntent(
                        GeneralUtilities.createShareIntent("http://www.youtube.com/watch?v=" + firstTrailer.getKey()));

            }
        } else {
            Log.i("SHARE", "set Share = null ");

            mShareActionProvider.setShareIntent(null);
        }
    }


    ;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_movie_details, menu);

        Log.i("SHARE", "create optiopns menu");

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        mShareActionProvider = new ShareActionProvider(getActivity());
        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        shareTrailer();
    }
}

