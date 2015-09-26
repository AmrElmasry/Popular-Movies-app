package com.moviesapp.amrelmasry.popular_movies_app.provider.mostratedmovies;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.moviesapp.amrelmasry.popular_movies_app.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code most_rated_movies} table.
 */
public class MostRatedMoviesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MostRatedMoviesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MostRatedMoviesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MostRatedMoviesSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Movie tilte
     */
    public MostRatedMoviesContentValues putTitle(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("title must not be null");
        mContentValues.put(MostRatedMoviesColumns.TITLE, value);
        return this;
    }


    /**
     * Movie ID in the API
     */
    public MostRatedMoviesContentValues putApiId(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("apiId must not be null");
        mContentValues.put(MostRatedMoviesColumns.API_ID, value);
        return this;
    }


    /**
     * Movie plot
     */
    public MostRatedMoviesContentValues putOverview(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("overview must not be null");
        mContentValues.put(MostRatedMoviesColumns.OVERVIEW, value);
        return this;
    }


    /**
     * Movie release date
     */
    public MostRatedMoviesContentValues putReleaseDate(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("releaseDate must not be null");
        mContentValues.put(MostRatedMoviesColumns.RELEASE_DATE, value);
        return this;
    }


    /**
     * Movie poster path
     */
    public MostRatedMoviesContentValues putPosterPath(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("posterPath must not be null");
        mContentValues.put(MostRatedMoviesColumns.POSTER_PATH, value);
        return this;
    }


    /**
     * Movie vote average
     */
    public MostRatedMoviesContentValues putVoteAverage(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("voteAverage must not be null");
        mContentValues.put(MostRatedMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

}
