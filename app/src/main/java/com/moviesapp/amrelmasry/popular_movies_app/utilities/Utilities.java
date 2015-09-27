package com.moviesapp.amrelmasry.popular_movies_app.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.moviesapp.amrelmasry.popular_movies_app.R;
import com.moviesapp.amrelmasry.popular_movies_app.provider.favoritesmovies.FavoritesMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.mostratedmovies.MostRatedMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.popularmovies.PopularMoviesColumns;

/**
 * Created by AmrELmasry on 9/26/2015.
 */
public class Utilities {

    public static final int COL_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_API_ID = 2;
    public static final int COL_OVERVIEW = 3;
    public static final int COL_RELEASE_DATE = 4;
    public static final int COL_POSTER_PATH = 5;
    public static final int COL_VOTE_AVERAGE = 6;


    public static String getShowMoviesBy(Context context) {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        String showMoviesby = sharedPrefs.getString(context.getString(R.string.pref_sort_by_key),
                context.getString(R.string.pref_sort_by_popular));

        return showMoviesby;

    }

    public static Uri getTableUri(String showMoviesBy, Context context) {
        Uri tableUri = null;
        if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_popular))) {

            tableUri = PopularMoviesColumns.CONTENT_URI;

        } else if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_most_rated))) {

            tableUri = MostRatedMoviesColumns.CONTENT_URI;
        }
        else if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_favorites))) {

            tableUri = FavoritesMoviesColumns.CONTENT_URI;
        }

        return tableUri;
    }

    public static String getTableName(String showMoviesBy, Context context) {

        String tableName = null;
        if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_popular))) {

            tableName = PopularMoviesColumns.TABLE_NAME;

        } else if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_most_rated))) {

            tableName = MostRatedMoviesColumns.TABLE_NAME;
        } else if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_favorites))) {

            tableName = FavoritesMoviesColumns.TABLE_NAME;
        }


        return tableName;
    }
}
