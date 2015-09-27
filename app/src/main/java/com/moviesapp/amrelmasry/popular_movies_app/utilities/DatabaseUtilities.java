package com.moviesapp.amrelmasry.popular_movies_app.utilities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.moviesapp.amrelmasry.popular_movies_app.provider.favoritesmovies.FavoritesMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.popularmovies.PopularMoviesContentValues;

/**
 * Created by AmrELmasry on 9/27/2015.
 */
public class DatabaseUtilities {

    public static void insertIntoDatabase(String api_id, String title, String overview, String release_date, String vote_average, String poster_path, Uri ContentUri, Context context) {

        PopularMoviesContentValues contentValues = new PopularMoviesContentValues(); // TODO USE MORE GENERIC OR USE BULD INSERT WITH DEFAULT CONTENTVALUES

        contentValues.putApiId(api_id)
                .putTitle(title)
                .putOverview(overview)
                .putReleaseDate(release_date)
                .putVoteAverage(vote_average)
                .putPosterPath(poster_path);

        context.getContentResolver().insert(ContentUri, contentValues.values());

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

    // TODO AD TRY / FINALLY TO CLOSE CURSOR

    public static void changeFavoriteState(String api_id, String title, String overview, String release_date, String vote_average, String poster_path, Context context) {

        Cursor cursor = getMovieFromDB(api_id, FavoritesMoviesColumns.TABLE_NAME, FavoritesMoviesColumns.CONTENT_URI, context);


        if (cursor != null && cursor.getCount() > 0) {

            // remove from favorites
            removeFromDatabase(api_id, FavoritesMoviesColumns.TABLE_NAME, FavoritesMoviesColumns.CONTENT_URI, context);
            Toast.makeText(context, "Movie removed", Toast.LENGTH_SHORT).show();


        } else {

            // add to favorites
            insertIntoDatabase(api_id, title, overview, release_date, vote_average, poster_path, FavoritesMoviesColumns.CONTENT_URI, context);
            Toast.makeText(context, "Movie added", Toast.LENGTH_SHORT).show();

        }
    }
}









