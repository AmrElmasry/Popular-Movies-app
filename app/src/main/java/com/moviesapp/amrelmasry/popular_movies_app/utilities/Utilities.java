package com.moviesapp.amrelmasry.popular_movies_app.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.moviesapp.amrelmasry.popular_movies_app.R;

/**
 * Created by AmrELmasry on 9/26/2015.
 */
public class Utilities {
    public static String getShowMoviesBy(Context context) {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        String showMoviesby = sharedPrefs.getString(context.getString(R.string.pref_sort_by_key),
                context.getString(R.string.pref_sort_by_popular));

        return showMoviesby;

    }
}
