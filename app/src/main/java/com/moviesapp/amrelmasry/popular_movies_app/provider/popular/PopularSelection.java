package com.moviesapp.amrelmasry.popular_movies_app.provider.popular;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.moviesapp.amrelmasry.popular_movies_app.provider.base.AbstractSelection;

/**
 * Selection for the {@code popular} table.
 */
public class PopularSelection extends AbstractSelection<PopularSelection> {
    @Override
    protected Uri baseUri() {
        return PopularColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PopularCursor} object, which is positioned before the first entry, or null.
     */
    public PopularCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PopularCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public PopularCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PopularCursor} object, which is positioned before the first entry, or null.
     */
    public PopularCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PopularCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public PopularCursor query(Context context) {
        return query(context, null);
    }


    public PopularSelection id(long... value) {
        addEquals("popular." + PopularColumns._ID, toObjectArray(value));
        return this;
    }

    public PopularSelection idNot(long... value) {
        addNotEquals("popular." + PopularColumns._ID, toObjectArray(value));
        return this;
    }

    public PopularSelection orderById(boolean desc) {
        orderBy("popular." + PopularColumns._ID, desc);
        return this;
    }

    public PopularSelection orderById() {
        return orderById(false);
    }

    public PopularSelection title(String... value) {
        addEquals(PopularColumns.TITLE, value);
        return this;
    }

    public PopularSelection titleNot(String... value) {
        addNotEquals(PopularColumns.TITLE, value);
        return this;
    }

    public PopularSelection titleLike(String... value) {
        addLike(PopularColumns.TITLE, value);
        return this;
    }

    public PopularSelection titleContains(String... value) {
        addContains(PopularColumns.TITLE, value);
        return this;
    }

    public PopularSelection titleStartsWith(String... value) {
        addStartsWith(PopularColumns.TITLE, value);
        return this;
    }

    public PopularSelection titleEndsWith(String... value) {
        addEndsWith(PopularColumns.TITLE, value);
        return this;
    }

    public PopularSelection orderByTitle(boolean desc) {
        orderBy(PopularColumns.TITLE, desc);
        return this;
    }

    public PopularSelection orderByTitle() {
        orderBy(PopularColumns.TITLE, false);
        return this;
    }

    public PopularSelection overview(String... value) {
        addEquals(PopularColumns.OVERVIEW, value);
        return this;
    }

    public PopularSelection overviewNot(String... value) {
        addNotEquals(PopularColumns.OVERVIEW, value);
        return this;
    }

    public PopularSelection overviewLike(String... value) {
        addLike(PopularColumns.OVERVIEW, value);
        return this;
    }

    public PopularSelection overviewContains(String... value) {
        addContains(PopularColumns.OVERVIEW, value);
        return this;
    }

    public PopularSelection overviewStartsWith(String... value) {
        addStartsWith(PopularColumns.OVERVIEW, value);
        return this;
    }

    public PopularSelection overviewEndsWith(String... value) {
        addEndsWith(PopularColumns.OVERVIEW, value);
        return this;
    }

    public PopularSelection orderByOverview(boolean desc) {
        orderBy(PopularColumns.OVERVIEW, desc);
        return this;
    }

    public PopularSelection orderByOverview() {
        orderBy(PopularColumns.OVERVIEW, false);
        return this;
    }

    public PopularSelection releaseDate(String... value) {
        addEquals(PopularColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularSelection releaseDateNot(String... value) {
        addNotEquals(PopularColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularSelection releaseDateLike(String... value) {
        addLike(PopularColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularSelection releaseDateContains(String... value) {
        addContains(PopularColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularSelection releaseDateStartsWith(String... value) {
        addStartsWith(PopularColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularSelection releaseDateEndsWith(String... value) {
        addEndsWith(PopularColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularSelection orderByReleaseDate(boolean desc) {
        orderBy(PopularColumns.RELEASE_DATE, desc);
        return this;
    }

    public PopularSelection orderByReleaseDate() {
        orderBy(PopularColumns.RELEASE_DATE, false);
        return this;
    }

    public PopularSelection posterPath(String... value) {
        addEquals(PopularColumns.POSTER_PATH, value);
        return this;
    }

    public PopularSelection posterPathNot(String... value) {
        addNotEquals(PopularColumns.POSTER_PATH, value);
        return this;
    }

    public PopularSelection posterPathLike(String... value) {
        addLike(PopularColumns.POSTER_PATH, value);
        return this;
    }

    public PopularSelection posterPathContains(String... value) {
        addContains(PopularColumns.POSTER_PATH, value);
        return this;
    }

    public PopularSelection posterPathStartsWith(String... value) {
        addStartsWith(PopularColumns.POSTER_PATH, value);
        return this;
    }

    public PopularSelection posterPathEndsWith(String... value) {
        addEndsWith(PopularColumns.POSTER_PATH, value);
        return this;
    }

    public PopularSelection orderByPosterPath(boolean desc) {
        orderBy(PopularColumns.POSTER_PATH, desc);
        return this;
    }

    public PopularSelection orderByPosterPath() {
        orderBy(PopularColumns.POSTER_PATH, false);
        return this;
    }

    public PopularSelection voteAverage(String... value) {
        addEquals(PopularColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularSelection voteAverageNot(String... value) {
        addNotEquals(PopularColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularSelection voteAverageLike(String... value) {
        addLike(PopularColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularSelection voteAverageContains(String... value) {
        addContains(PopularColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularSelection voteAverageStartsWith(String... value) {
        addStartsWith(PopularColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularSelection voteAverageEndsWith(String... value) {
        addEndsWith(PopularColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularSelection orderByVoteAverage(boolean desc) {
        orderBy(PopularColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public PopularSelection orderByVoteAverage() {
        orderBy(PopularColumns.VOTE_AVERAGE, false);
        return this;
    }
}
