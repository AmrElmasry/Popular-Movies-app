package com.moviesapp.amrelmasry.popular_movies_app.provider.popular;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.moviesapp.amrelmasry.popular_movies_app.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code popular} table.
 */
public class PopularContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PopularColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable PopularSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable PopularSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Movie tilte
     */
    public PopularContentValues putTitle(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("title must not be null");
        mContentValues.put(PopularColumns.TITLE, value);
        return this;
    }


    /**
     * Movie plot
     */
    public PopularContentValues putOverview(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("overview must not be null");
        mContentValues.put(PopularColumns.OVERVIEW, value);
        return this;
    }


    /**
     * Movie release date
     */
    public PopularContentValues putReleaseDate(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("releaseDate must not be null");
        mContentValues.put(PopularColumns.RELEASE_DATE, value);
        return this;
    }


    /**
     * Movie poster path
     */
    public PopularContentValues putPosterPath(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("posterPath must not be null");
        mContentValues.put(PopularColumns.POSTER_PATH, value);
        return this;
    }


    /**
     * Movie vote average
     */
    public PopularContentValues putVoteAverage(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("voteAverage must not be null");
        mContentValues.put(PopularColumns.VOTE_AVERAGE, value);
        return this;
    }

}
