package com.moviesapp.amrelmasry.popular_movies_app.utilities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.moviesapp.amrelmasry.popular_movies_app.R;
import com.moviesapp.amrelmasry.popular_movies_app.provider.helper.MoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.helper.MoviesContentValues;

/**
 * Created by AmrELmasry on 9/27/2015.
 */
public class DatabaseUtilities {


    public static final int COL_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_API_ID = 2;
    public static final int COL_OVERVIEW = 3;
    public static final int COL_RELEASE_DATE = 4;
    public static final int COL_POSTER_PATH = 5;
    public static final int COL_VOTE_AVERAGE = 6;

    public static void insertIntoDatabase(String api_id, String title, String overview, String release_date, String vote_average, String poster_path, Uri contentUri, Context context) {

        MoviesContentValues contentValues = new MoviesContentValues(contentUri); // TODO USE MORE GENERIC OR USE BULk INSERT WITH DEFAULT CONTENTVALUES

        contentValues.putApiId(api_id)
                .putTitle(title)
                .putOverview(overview)
                .putReleaseDate(release_date)
                .putVoteAverage(vote_average)
                .putPosterPath(poster_path);

        context.getContentResolver().insert(contentUri, contentValues.values());

    }


    public static Cursor getMovieFromDB(String movieApiId, String tableName, Uri contentUri, Context context) {

        String selection = tableName + ".api_id = ? ";
        String[] selectionArgs = new String[]{movieApiId};

        Cursor cursor = context.getContentResolver().query(contentUri, null, selection, selectionArgs, null);


        if (cursor.getCount() > 0 && cursor.moveToNext()) {
            return cursor;

        }
        return null;

    }

    public static void removeFromDatabase(String movieApiID, String tableName, Uri contentUri, Context context) {

        String selection = tableName + ".api_id = ? ";

        String[] selectionArgs = new String[]{movieApiID};


        context.getContentResolver().delete(contentUri,
                selection, selectionArgs); // TODO CHECK IF = -1

    }


    public static boolean isFavoriteMovie(String api_id, Context context) {

        try {
            Cursor cursor = getMovieFromDB(api_id, MoviesColumns.FAVORITES_TABLE_NAME, MoviesColumns.FAVORITES_CONTENT_URI, context);

            if (cursor != null && cursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {

            return false;
        }


    }


    public static Uri getTableUri(String showMoviesBy, Context context) {
        Uri tableUri = null;
        if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_popular))) {

            tableUri = MoviesColumns.POPULAR_CONTENT_URI;

        } else if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_most_rated))) {

            tableUri = MoviesColumns.MOST_RATED_CONTENT_URI;
        } else if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_favorites))) {

            tableUri = MoviesColumns.FAVORITES_CONTENT_URI;
        }

        return tableUri;
    }

    public static String getTableName(String showMoviesBy, Context context) {

        String tableName = null;
        if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_popular))) {

            tableName = MoviesColumns.POPULAR_TABLE_NAME;

        } else if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_most_rated))) {

            tableName = MoviesColumns.MOST_RATED_TABLE_NAME;
        } else if (showMoviesBy.equals(context.getString(R.string.pref_sort_by_favorites))) {

            tableName = MoviesColumns.FAVORITES_TABLE_NAME;
        }


        return tableName;
    }


}









