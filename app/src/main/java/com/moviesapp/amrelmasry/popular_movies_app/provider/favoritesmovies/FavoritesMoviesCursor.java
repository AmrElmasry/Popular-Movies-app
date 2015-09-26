package com.moviesapp.amrelmasry.popular_movies_app.provider.favoritesmovies;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.moviesapp.amrelmasry.popular_movies_app.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code favorites_movies} table.
 */
public class FavoritesMoviesCursor extends AbstractCursor implements FavoritesMoviesModel {
    public FavoritesMoviesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(FavoritesMoviesColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie tilte
     * Cannot be {@code null}.
     */
    @NonNull
    public String getTitle() {
        String res = getStringOrNull(FavoritesMoviesColumns.TITLE);
        if (res == null)
            throw new NullPointerException("The value of 'title' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie ID in the API
     * Cannot be {@code null}.
     */
    @NonNull
    public String getApiId() {
        String res = getStringOrNull(FavoritesMoviesColumns.API_ID);
        if (res == null)
            throw new NullPointerException("The value of 'api_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie plot
     * Cannot be {@code null}.
     */
    @NonNull
    public String getOverview() {
        String res = getStringOrNull(FavoritesMoviesColumns.OVERVIEW);
        if (res == null)
            throw new NullPointerException("The value of 'overview' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie release date
     * Cannot be {@code null}.
     */
    @NonNull
    public String getReleaseDate() {
        String res = getStringOrNull(FavoritesMoviesColumns.RELEASE_DATE);
        if (res == null)
            throw new NullPointerException("The value of 'release_date' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie poster path
     * Cannot be {@code null}.
     */
    @NonNull
    public String getPosterPath() {
        String res = getStringOrNull(FavoritesMoviesColumns.POSTER_PATH);
        if (res == null)
            throw new NullPointerException("The value of 'poster_path' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie vote average
     * Cannot be {@code null}.
     */
    @NonNull
    public String getVoteAverage() {
        String res = getStringOrNull(FavoritesMoviesColumns.VOTE_AVERAGE);
        if (res == null)
            throw new NullPointerException("The value of 'vote_average' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
