package com.moviesapp.amrelmasry.popular_movies_app.provider.helper;

import android.net.Uri;
import android.provider.BaseColumns;

import com.moviesapp.amrelmasry.popular_movies_app.provider.MoviesProvider;

/**
 * Created by AmrELmasry on 10/3/2015.
 */
public class MoviesColumns implements BaseColumns {

    public static final String FAVORITES_TABLE_NAME = "favorites_movies";
    public static final Uri FAVORITES_CONTENT_URI = Uri.parse(MoviesProvider.CONTENT_URI_BASE + "/" + FAVORITES_TABLE_NAME);


    public static final String POPULAR_TABLE_NAME = "popular_movies";
    public static final Uri POPULAR_CONTENT_URI = Uri.parse(MoviesProvider.CONTENT_URI_BASE + "/" + POPULAR_TABLE_NAME);


    public static final String MOST_RATED_TABLE_NAME = "most_rated_movies";
    public static final Uri MOST_RATED_CONTENT_URI = Uri.parse(MoviesProvider.CONTENT_URI_BASE + "/" + MOST_RATED_TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Movie tilte
     */
    public static final String TITLE = "title";

    /**
     * Movie ID in the API
     */
    public static final String API_ID = "api_id";

    /**
     * Movie plot
     */
    public static final String OVERVIEW = "overview";

    /**
     * Movie release date
     */
    public static final String RELEASE_DATE = "release_date";

    /**
     * Movie poster path
     */
    public static final String POSTER_PATH = "poster_path";

    /**
     * Movie vote average
     */
    public static final String VOTE_AVERAGE = "vote_average";


    public static final String FAVORITES_DEFAULT_ORDER = FAVORITES_TABLE_NAME + "." + _ID;
    public static final String POPULAR_DEFAULT_ORDER = POPULAR_TABLE_NAME + "." + _ID;
    public static final String MOST_RATED_DEFAULT_ORDER = MOST_RATED_TABLE_NAME + "." + _ID;


    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[]{
            _ID,
            TITLE,
            API_ID,
            OVERVIEW,
            RELEASE_DATE,
            POSTER_PATH,
            VOTE_AVERAGE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(API_ID) || c.contains("." + API_ID)) return true;
            if (c.equals(OVERVIEW) || c.contains("." + OVERVIEW)) return true;
            if (c.equals(RELEASE_DATE) || c.contains("." + RELEASE_DATE)) return true;
            if (c.equals(POSTER_PATH) || c.contains("." + POSTER_PATH)) return true;
            if (c.equals(VOTE_AVERAGE) || c.contains("." + VOTE_AVERAGE)) return true;
        }
        return false;
    }

}
