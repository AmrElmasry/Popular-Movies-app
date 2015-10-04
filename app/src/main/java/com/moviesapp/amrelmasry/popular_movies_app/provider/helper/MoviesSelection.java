package com.moviesapp.amrelmasry.popular_movies_app.provider.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.moviesapp.amrelmasry.popular_movies_app.provider.base.AbstractSelection;

/**
 * Created by AmrELmasry on 10/4/2015.
 */
public class MoviesSelection extends AbstractSelection<MoviesSelection> {

    @Override
    protected Uri baseUri() {
        return MoviesColumns.FAVORITES_CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection      A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MoviesCursor} object, which is positioned before the first entry, or null.
     */
    public MoviesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MoviesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public MoviesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context    The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MoviesCursor} object, which is positioned before the first entry, or null.
     */
    public MoviesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MoviesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public MoviesCursor query(Context context) {
        return query(context, null);
    }


    public MoviesSelection id(long... value) {
        addEquals("favorites_movies." + MoviesColumns._ID, toObjectArray(value));
        return this;
    }

    public MoviesSelection idNot(long... value) {
        addNotEquals("favorites_movies." + MoviesColumns._ID, toObjectArray(value));
        return this;
    }

    public MoviesSelection orderById(boolean desc) {
        orderBy("favorites_movies." + MoviesColumns._ID, desc);
        return this;
    }

    public MoviesSelection orderById() {
        return orderById(false);
    }

    public MoviesSelection title(String... value) {
        addEquals(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection titleNot(String... value) {
        addNotEquals(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection titleLike(String... value) {
        addLike(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection titleContains(String... value) {
        addContains(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection titleStartsWith(String... value) {
        addStartsWith(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection titleEndsWith(String... value) {
        addEndsWith(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection orderByTitle(boolean desc) {
        orderBy(MoviesColumns.TITLE, desc);
        return this;
    }

    public MoviesSelection orderByTitle() {
        orderBy(MoviesColumns.TITLE, false);
        return this;
    }

    public MoviesSelection apiId(String... value) {
        addEquals(MoviesColumns.API_ID, value);
        return this;
    }

    public MoviesSelection apiIdNot(String... value) {
        addNotEquals(MoviesColumns.API_ID, value);
        return this;
    }

    public MoviesSelection apiIdLike(String... value) {
        addLike(MoviesColumns.API_ID, value);
        return this;
    }

    public MoviesSelection apiIdContains(String... value) {
        addContains(MoviesColumns.API_ID, value);
        return this;
    }

    public MoviesSelection apiIdStartsWith(String... value) {
        addStartsWith(MoviesColumns.API_ID, value);
        return this;
    }

    public MoviesSelection apiIdEndsWith(String... value) {
        addEndsWith(MoviesColumns.API_ID, value);
        return this;
    }

    public MoviesSelection orderByApiId(boolean desc) {
        orderBy(MoviesColumns.API_ID, desc);
        return this;
    }

    public MoviesSelection orderByApiId() {
        orderBy(MoviesColumns.API_ID, false);
        return this;
    }

    public MoviesSelection overview(String... value) {
        addEquals(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection overviewNot(String... value) {
        addNotEquals(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection overviewLike(String... value) {
        addLike(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection overviewContains(String... value) {
        addContains(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection overviewStartsWith(String... value) {
        addStartsWith(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection overviewEndsWith(String... value) {
        addEndsWith(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection orderByOverview(boolean desc) {
        orderBy(MoviesColumns.OVERVIEW, desc);
        return this;
    }

    public MoviesSelection orderByOverview() {
        orderBy(MoviesColumns.OVERVIEW, false);
        return this;
    }

    public MoviesSelection releaseDate(String... value) {
        addEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection releaseDateNot(String... value) {
        addNotEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection releaseDateLike(String... value) {
        addLike(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection releaseDateContains(String... value) {
        addContains(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection releaseDateStartsWith(String... value) {
        addStartsWith(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection releaseDateEndsWith(String... value) {
        addEndsWith(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection orderByReleaseDate(boolean desc) {
        orderBy(MoviesColumns.RELEASE_DATE, desc);
        return this;
    }

    public MoviesSelection orderByReleaseDate() {
        orderBy(MoviesColumns.RELEASE_DATE, false);
        return this;
    }

    public MoviesSelection posterPath(String... value) {
        addEquals(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection posterPathNot(String... value) {
        addNotEquals(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection posterPathLike(String... value) {
        addLike(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection posterPathContains(String... value) {
        addContains(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection posterPathStartsWith(String... value) {
        addStartsWith(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection posterPathEndsWith(String... value) {
        addEndsWith(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection orderByPosterPath(boolean desc) {
        orderBy(MoviesColumns.POSTER_PATH, desc);
        return this;
    }

    public MoviesSelection orderByPosterPath() {
        orderBy(MoviesColumns.POSTER_PATH, false);
        return this;
    }

    public MoviesSelection voteAverage(String... value) {
        addEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection voteAverageNot(String... value) {
        addNotEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection voteAverageLike(String... value) {
        addLike(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection voteAverageContains(String... value) {
        addContains(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection voteAverageStartsWith(String... value) {
        addStartsWith(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection voteAverageEndsWith(String... value) {
        addEndsWith(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection orderByVoteAverage(boolean desc) {
        orderBy(MoviesColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public MoviesSelection orderByVoteAverage() {
        orderBy(MoviesColumns.VOTE_AVERAGE, false);
        return this;
    }
}
