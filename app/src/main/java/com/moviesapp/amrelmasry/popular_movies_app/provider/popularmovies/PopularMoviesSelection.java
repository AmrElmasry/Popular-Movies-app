package com.moviesapp.amrelmasry.popular_movies_app.provider.popularmovies;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.moviesapp.amrelmasry.popular_movies_app.provider.base.AbstractSelection;

/**
 * Selection for the {@code popular_movies} table.
 */
public class PopularMoviesSelection extends AbstractSelection<PopularMoviesSelection> {
    @Override
    protected Uri baseUri() {
        return PopularMoviesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PopularMoviesCursor} object, which is positioned before the first entry, or null.
     */
    public PopularMoviesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PopularMoviesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public PopularMoviesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PopularMoviesCursor} object, which is positioned before the first entry, or null.
     */
    public PopularMoviesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PopularMoviesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public PopularMoviesCursor query(Context context) {
        return query(context, null);
    }


    public PopularMoviesSelection id(long... value) {
        addEquals("popular_movies." + PopularMoviesColumns._ID, toObjectArray(value));
        return this;
    }

    public PopularMoviesSelection idNot(long... value) {
        addNotEquals("popular_movies." + PopularMoviesColumns._ID, toObjectArray(value));
        return this;
    }

    public PopularMoviesSelection orderById(boolean desc) {
        orderBy("popular_movies." + PopularMoviesColumns._ID, desc);
        return this;
    }

    public PopularMoviesSelection orderById() {
        return orderById(false);
    }

    public PopularMoviesSelection title(String... value) {
        addEquals(PopularMoviesColumns.TITLE, value);
        return this;
    }

    public PopularMoviesSelection titleNot(String... value) {
        addNotEquals(PopularMoviesColumns.TITLE, value);
        return this;
    }

    public PopularMoviesSelection titleLike(String... value) {
        addLike(PopularMoviesColumns.TITLE, value);
        return this;
    }

    public PopularMoviesSelection titleContains(String... value) {
        addContains(PopularMoviesColumns.TITLE, value);
        return this;
    }

    public PopularMoviesSelection titleStartsWith(String... value) {
        addStartsWith(PopularMoviesColumns.TITLE, value);
        return this;
    }

    public PopularMoviesSelection titleEndsWith(String... value) {
        addEndsWith(PopularMoviesColumns.TITLE, value);
        return this;
    }

    public PopularMoviesSelection orderByTitle(boolean desc) {
        orderBy(PopularMoviesColumns.TITLE, desc);
        return this;
    }

    public PopularMoviesSelection orderByTitle() {
        orderBy(PopularMoviesColumns.TITLE, false);
        return this;
    }

    public PopularMoviesSelection apiId(String... value) {
        addEquals(PopularMoviesColumns.API_ID, value);
        return this;
    }

    public PopularMoviesSelection apiIdNot(String... value) {
        addNotEquals(PopularMoviesColumns.API_ID, value);
        return this;
    }

    public PopularMoviesSelection apiIdLike(String... value) {
        addLike(PopularMoviesColumns.API_ID, value);
        return this;
    }

    public PopularMoviesSelection apiIdContains(String... value) {
        addContains(PopularMoviesColumns.API_ID, value);
        return this;
    }

    public PopularMoviesSelection apiIdStartsWith(String... value) {
        addStartsWith(PopularMoviesColumns.API_ID, value);
        return this;
    }

    public PopularMoviesSelection apiIdEndsWith(String... value) {
        addEndsWith(PopularMoviesColumns.API_ID, value);
        return this;
    }

    public PopularMoviesSelection orderByApiId(boolean desc) {
        orderBy(PopularMoviesColumns.API_ID, desc);
        return this;
    }

    public PopularMoviesSelection orderByApiId() {
        orderBy(PopularMoviesColumns.API_ID, false);
        return this;
    }

    public PopularMoviesSelection overview(String... value) {
        addEquals(PopularMoviesColumns.OVERVIEW, value);
        return this;
    }

    public PopularMoviesSelection overviewNot(String... value) {
        addNotEquals(PopularMoviesColumns.OVERVIEW, value);
        return this;
    }

    public PopularMoviesSelection overviewLike(String... value) {
        addLike(PopularMoviesColumns.OVERVIEW, value);
        return this;
    }

    public PopularMoviesSelection overviewContains(String... value) {
        addContains(PopularMoviesColumns.OVERVIEW, value);
        return this;
    }

    public PopularMoviesSelection overviewStartsWith(String... value) {
        addStartsWith(PopularMoviesColumns.OVERVIEW, value);
        return this;
    }

    public PopularMoviesSelection overviewEndsWith(String... value) {
        addEndsWith(PopularMoviesColumns.OVERVIEW, value);
        return this;
    }

    public PopularMoviesSelection orderByOverview(boolean desc) {
        orderBy(PopularMoviesColumns.OVERVIEW, desc);
        return this;
    }

    public PopularMoviesSelection orderByOverview() {
        orderBy(PopularMoviesColumns.OVERVIEW, false);
        return this;
    }

    public PopularMoviesSelection releaseDate(String... value) {
        addEquals(PopularMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularMoviesSelection releaseDateNot(String... value) {
        addNotEquals(PopularMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularMoviesSelection releaseDateLike(String... value) {
        addLike(PopularMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularMoviesSelection releaseDateContains(String... value) {
        addContains(PopularMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularMoviesSelection releaseDateStartsWith(String... value) {
        addStartsWith(PopularMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularMoviesSelection releaseDateEndsWith(String... value) {
        addEndsWith(PopularMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public PopularMoviesSelection orderByReleaseDate(boolean desc) {
        orderBy(PopularMoviesColumns.RELEASE_DATE, desc);
        return this;
    }

    public PopularMoviesSelection orderByReleaseDate() {
        orderBy(PopularMoviesColumns.RELEASE_DATE, false);
        return this;
    }

    public PopularMoviesSelection posterPath(String... value) {
        addEquals(PopularMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public PopularMoviesSelection posterPathNot(String... value) {
        addNotEquals(PopularMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public PopularMoviesSelection posterPathLike(String... value) {
        addLike(PopularMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public PopularMoviesSelection posterPathContains(String... value) {
        addContains(PopularMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public PopularMoviesSelection posterPathStartsWith(String... value) {
        addStartsWith(PopularMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public PopularMoviesSelection posterPathEndsWith(String... value) {
        addEndsWith(PopularMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public PopularMoviesSelection orderByPosterPath(boolean desc) {
        orderBy(PopularMoviesColumns.POSTER_PATH, desc);
        return this;
    }

    public PopularMoviesSelection orderByPosterPath() {
        orderBy(PopularMoviesColumns.POSTER_PATH, false);
        return this;
    }

    public PopularMoviesSelection voteAverage(String... value) {
        addEquals(PopularMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularMoviesSelection voteAverageNot(String... value) {
        addNotEquals(PopularMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularMoviesSelection voteAverageLike(String... value) {
        addLike(PopularMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularMoviesSelection voteAverageContains(String... value) {
        addContains(PopularMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularMoviesSelection voteAverageStartsWith(String... value) {
        addStartsWith(PopularMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularMoviesSelection voteAverageEndsWith(String... value) {
        addEndsWith(PopularMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public PopularMoviesSelection orderByVoteAverage(boolean desc) {
        orderBy(PopularMoviesColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public PopularMoviesSelection orderByVoteAverage() {
        orderBy(PopularMoviesColumns.VOTE_AVERAGE, false);
        return this;
    }
}
