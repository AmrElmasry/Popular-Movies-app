package com.moviesapp.amrelmasry.popular_movies_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
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

import com.moviesapp.amrelmasry.popular_movies_app.adapters.ReviewsAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.adapters.TrailersAdapter;
import com.moviesapp.amrelmasry.popular_movies_app.loaders.ReviewsLoader;
import com.moviesapp.amrelmasry.popular_movies_app.loaders.TrailersLoader;
import com.moviesapp.amrelmasry.popular_movies_app.models.Trailer;
import com.moviesapp.amrelmasry.popular_movies_app.provider.helper.MoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.helper.MoviesCursor;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.DatabaseUtilities;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.GeneralUtilities;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List> {

    private String movieApiId;
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
    private MenuItem menuShareItem;

    private FloatingActionButton favorite;
    private static final int TRAILERS_LOADER_ID = 1;
    private static final int REVIEWS_LOADER_ID = 2;
    private TextView no_movie_view;


    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        setHasOptionsMenu(true);

        View exist_movie_view = rootView.findViewById(R.id.exist_movie_view);
        no_movie_view = (TextView) rootView.findViewById(R.id.no_movie_view);


        ImageView moviePoster = (ImageView) rootView.findViewById(R.id.movie_poster);
        favorite = (FloatingActionButton) rootView.findViewById(R.id.fab);
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
            favorite.setVisibility(View.VISIBLE);
            no_movie_view.setVisibility(View.GONE);
            exist_movie_view.setVisibility(View.VISIBLE);

            movieApiId = arguments.getString(getString(R.string.movie_api_id));
            tableUri = arguments.getString(getString(R.string.table_Uri));


            MoviesCursor movieCursor = DatabaseUtilities.getMovieFromDB(movieApiId, Uri.parse(tableUri), getActivity());


            movieTitle = movieCursor.getTitle();
            movieOverview = movieCursor.getOverview();
            movieVoteAverage = movieCursor.getVoteAverage();
            movieReleaseDate = movieCursor.getReleaseDate();
            moviePosterPath = movieCursor.getPosterPath();


            movieCursor.close();


            final String BASE_MOVIE_IMAGE_URL = "http://image.tmdb.org/t/p";

            Uri uri = Uri.parse(BASE_MOVIE_IMAGE_URL)
                    .buildUpon()
                    .appendPath("w185")
                    .appendEncodedPath(moviePosterPath)
                    .build();


            Picasso.with(getActivity()).load(uri).into(moviePoster);
            movieTitleTextView.setText(movieTitle);
            moviePlotTextView.setText(movieOverview);
            movieRatingTextView.setText(movieVoteAverage);
            movieDateTextView.setText(movieReleaseDate);

            moviePoster.setContentDescription(getString(R.string.movie_poster_desc) + movieTitle);
        } else {
            favorite.setVisibility(View.GONE);
            exist_movie_view.setVisibility(View.GONE);
            no_movie_view.setVisibility(View.VISIBLE);

        }


        if (tableUri != null && tableUri.equals(MoviesColumns.FAVORITES_CONTENT_URI.toString())) {
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


        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isFavoriteMovie) {
                    DatabaseUtilities.removeFromFavorites(movieApiId, getActivity());
                    isFavoriteMovie = false;
                    changeFavoriteButtonState();
                } else {
                    DatabaseUtilities.insertIntoDatabase(movieApiId, movieTitle,
                            movieOverview, movieReleaseDate, movieVoteAverage,
                            moviePosterPath, MoviesColumns.FAVORITES_CONTENT_URI, getActivity());
                    isFavoriteMovie = true;
                    changeFavoriteButtonState();
                }


            }
        });


        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trailer trailer = (Trailer) parent.getItemAtPosition(position);

                Intent intent = new Intent(Intent.ACTION_VIEW, trailer.getUri());
                startActivity(intent);

            }
        });

        return rootView;
    }

    private void changeFavoriteButtonState() {

        if (isFavoriteMovie) {
            favorite.setImageResource(R.drawable.ic_heart_red_filled);
        } else {
            favorite.setImageResource(R.drawable.ic_heart_outline_red);

        }
    }

    public static MovieDetailsFragment newInstance(String movieApiId, String tableUri, Context context) {
        MovieDetailsFragment f = new MovieDetailsFragment();

        Bundle arguments = new Bundle();
        arguments.putString(context.getString(R.string.movie_api_id), movieApiId);


        arguments.putString(context.getString(R.string.table_Uri), tableUri);

        f.setArguments(arguments);


        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        if (movieApiId != null) {
            getLoaderManager().initLoader(TRAILERS_LOADER_ID, null, this).forceLoad();
            getLoaderManager().initLoader(REVIEWS_LOADER_ID, null, this).forceLoad();
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {

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
                        if (menuShareItem != null) {
                            menuShareItem.setVisible(false);
                        }


                    }

                } else {
                    // no internet

                    cannotLoadTrailers.setVisibility(View.VISIBLE);
                    if (menuShareItem != null) {
                        menuShareItem.setVisible(false);
                    }
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


    }

    private void shareTrailer() {
        if (trailersAdapter != null && trailersAdapter.getCount() > 0) {


            Trailer firstTrailer = trailersAdapter.getItem(0);
            if (firstTrailer != null) {


                mShareActionProvider.setShareIntent(
                        GeneralUtilities.createShareIntent("http://www.youtube.com/watch?v=" + firstTrailer.getKey()));

            }
        } else {

            mShareActionProvider.setShareIntent(null);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_movie_details, menu);


        // Retrieve the share menu item
        menuShareItem = menu.findItem(R.id.action_share);

        mShareActionProvider = new ShareActionProvider(getActivity());
        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuShareItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        shareTrailer();

        if ((menuShareItem != null && (noTrailers.getVisibility() == View.VISIBLE
                || cannotLoadTrailers.getVisibility() == View.VISIBLE))
                || no_movie_view.getVisibility() == View.VISIBLE) {
            menuShareItem.setVisible(false);
        }

    }
}

