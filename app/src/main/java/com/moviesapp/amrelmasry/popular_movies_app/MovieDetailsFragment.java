package com.moviesapp.amrelmasry.popular_movies_app;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.moviesapp.amrelmasry.popular_movies_app.utilities.DatabaseUtilities;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.Utilities;
import com.squareup.picasso.Picasso;

public class MovieDetailsFragment extends Fragment {

    String movieApiId;
    String tableName;
    String tableUri;

    String movieTitle;
    String movieOverview;
    String movieReleaseDate;
    String movieVoteAverage;
    String moviePosterPath;


    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Log.i("CREATE", "FRAGMENT CREATED");

        ImageView moviePoster = (ImageView) rootView.findViewById(R.id.movie_poster);

        TextView movieTitleTextView = (TextView) rootView.findViewById(R.id.movie_title);
        TextView moviePlotTextView = (TextView) rootView.findViewById(R.id.movie_plot);  // overview
        TextView movieRatingTextView = (TextView) rootView.findViewById(R.id.movie_rating); //vote_average
        TextView movieDateTextView = (TextView) rootView.findViewById(R.id.movie_release_date); //release date

        ListView reviewsListView = (ListView) rootView.findViewById(R.id.reviews_list);
        ListView trailersListView = (ListView) rootView.findViewById(R.id.trailers_list);

        Button favorites = (Button) rootView.findViewById(R.id.add_to_favorites);
        final Bundle arguments = getArguments();

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

        if (arguments != null) {

            movieApiId = arguments.getString(getString(R.string.movie_api_id));
            tableName = arguments.getString(getString(R.string.table_name));
            tableUri = arguments.getString(getString(R.string.table_Uri));



            Cursor movieCursor = DatabaseUtilities.getMovieFromDB(movieApiId, tableName, Uri.parse(tableUri), getActivity());

            try {
                movieTitle = movieCursor.getString(Utilities.COL_TITLE);
                movieOverview = movieCursor.getString(Utilities.COL_OVERVIEW);
                movieVoteAverage = movieCursor.getString(Utilities.COL_VOTE_AVERAGE);
                movieReleaseDate = movieCursor.getString(Utilities.COL_RELEASE_DATE);
                moviePosterPath = movieCursor.getString(Utilities.COL_POSTER_PATH);
            } catch (Exception e) {

            }


//            // TODO WHICH IMG SIZE
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + moviePosterPath).into(moviePoster);
            movieTitleTextView.setText(movieTitle);
            moviePlotTextView.setText(movieOverview);
            movieRatingTextView.setText(movieVoteAverage);
            movieDateTextView.setText(movieReleaseDate);

        }


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

//    public int getArgs() {
//        return getArguments().getInt("index", 0);
//    }
}
