package com.moviesapp.amrelmasry.popular_movies_app.provider.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.moviesapp.amrelmasry.popular_movies_app.provider.base.AbstractContentValues;

/**
 * Created by AmrELmasry on 10/3/2015.
 */
public class MoviesContentValues extends AbstractContentValues {

    private Uri contentURI;

    public MoviesContentValues(Uri contentURI) {
        this.contentURI = contentURI;
    }

    @Override
    public Uri uri() {
        return contentURI;
    }


    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where           The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MoviesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where           The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MoviesSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Movie tilte
     */
    public MoviesContentValues putTitle(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("title must not be null");
        mContentValues.put(MoviesColumns.TITLE, value);
        return this;
    }


    /**
     * Movie ID in the API
     */
    public MoviesContentValues putApiId(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("apiId must not be null");
        mContentValues.put(MoviesColumns.API_ID, value);
        return this;
    }


    /**
     * Movie plot
     */
    public MoviesContentValues putOverview(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("overview must not be null");
        mContentValues.put(MoviesColumns.OVERVIEW, value);
        return this;
    }


    /**
     * Movie release date
     */
    public MoviesContentValues putReleaseDate(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("releaseDate must not be null");
        mContentValues.put(MoviesColumns.RELEASE_DATE, value);
        return this;
    }


    /**
     * Movie poster path
     */
    public MoviesContentValues putPosterPath(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("posterPath must not be null");
        mContentValues.put(MoviesColumns.POSTER_PATH, value);
        return this;
    }


    /**
     * Movie vote average
     */
    public MoviesContentValues putVoteAverage(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("voteAverage must not be null");
        mContentValues.put(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

}
