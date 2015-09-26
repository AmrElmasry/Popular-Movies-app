package com.moviesapp.amrelmasry.popular_movies_app.provider.favoritesmovies;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.moviesapp.amrelmasry.popular_movies_app.provider.base.AbstractSelection;

/**
 * Selection for the {@code favorites_movies} table.
 */
public class FavoritesMoviesSelection extends AbstractSelection<FavoritesMoviesSelection> {
    @Override
    protected Uri baseUri() {
        return FavoritesMoviesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavoritesMoviesCursor} object, which is positioned before the first entry, or null.
     */
    public FavoritesMoviesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavoritesMoviesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public FavoritesMoviesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavoritesMoviesCursor} object, which is positioned before the first entry, or null.
     */
    public FavoritesMoviesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavoritesMoviesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public FavoritesMoviesCursor query(Context context) {
        return query(context, null);
    }


    public FavoritesMoviesSelection id(long... value) {
        addEquals("favorites_movies." + FavoritesMoviesColumns._ID, toObjectArray(value));
        return this;
    }

    public FavoritesMoviesSelection idNot(long... value) {
        addNotEquals("favorites_movies." + FavoritesMoviesColumns._ID, toObjectArray(value));
        return this;
    }

    public FavoritesMoviesSelection orderById(boolean desc) {
        orderBy("favorites_movies." + FavoritesMoviesColumns._ID, desc);
        return this;
    }

    public FavoritesMoviesSelection orderById() {
        return orderById(false);
    }

    public FavoritesMoviesSelection title(String... value) {
        addEquals(FavoritesMoviesColumns.TITLE, value);
        return this;
    }

    public FavoritesMoviesSelection titleNot(String... value) {
        addNotEquals(FavoritesMoviesColumns.TITLE, value);
        return this;
    }

    public FavoritesMoviesSelection titleLike(String... value) {
        addLike(FavoritesMoviesColumns.TITLE, value);
        return this;
    }

    public FavoritesMoviesSelection titleContains(String... value) {
        addContains(FavoritesMoviesColumns.TITLE, value);
        return this;
    }

    public FavoritesMoviesSelection titleStartsWith(String... value) {
        addStartsWith(FavoritesMoviesColumns.TITLE, value);
        return this;
    }

    public FavoritesMoviesSelection titleEndsWith(String... value) {
        addEndsWith(FavoritesMoviesColumns.TITLE, value);
        return this;
    }

    public FavoritesMoviesSelection orderByTitle(boolean desc) {
        orderBy(FavoritesMoviesColumns.TITLE, desc);
        return this;
    }

    public FavoritesMoviesSelection orderByTitle() {
        orderBy(FavoritesMoviesColumns.TITLE, false);
        return this;
    }

    public FavoritesMoviesSelection apiId(String... value) {
        addEquals(FavoritesMoviesColumns.API_ID, value);
        return this;
    }

    public FavoritesMoviesSelection apiIdNot(String... value) {
        addNotEquals(FavoritesMoviesColumns.API_ID, value);
        return this;
    }

    public FavoritesMoviesSelection apiIdLike(String... value) {
        addLike(FavoritesMoviesColumns.API_ID, value);
        return this;
    }

    public FavoritesMoviesSelection apiIdContains(String... value) {
        addContains(FavoritesMoviesColumns.API_ID, value);
        return this;
    }

    public FavoritesMoviesSelection apiIdStartsWith(String... value) {
        addStartsWith(FavoritesMoviesColumns.API_ID, value);
        return this;
    }

    public FavoritesMoviesSelection apiIdEndsWith(String... value) {
        addEndsWith(FavoritesMoviesColumns.API_ID, value);
        return this;
    }

    public FavoritesMoviesSelection orderByApiId(boolean desc) {
        orderBy(FavoritesMoviesColumns.API_ID, desc);
        return this;
    }

    public FavoritesMoviesSelection orderByApiId() {
        orderBy(FavoritesMoviesColumns.API_ID, false);
        return this;
    }

    public FavoritesMoviesSelection overview(String... value) {
        addEquals(FavoritesMoviesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesMoviesSelection overviewNot(String... value) {
        addNotEquals(FavoritesMoviesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesMoviesSelection overviewLike(String... value) {
        addLike(FavoritesMoviesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesMoviesSelection overviewContains(String... value) {
        addContains(FavoritesMoviesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesMoviesSelection overviewStartsWith(String... value) {
        addStartsWith(FavoritesMoviesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesMoviesSelection overviewEndsWith(String... value) {
        addEndsWith(FavoritesMoviesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesMoviesSelection orderByOverview(boolean desc) {
        orderBy(FavoritesMoviesColumns.OVERVIEW, desc);
        return this;
    }

    public FavoritesMoviesSelection orderByOverview() {
        orderBy(FavoritesMoviesColumns.OVERVIEW, false);
        return this;
    }

    public FavoritesMoviesSelection releaseDate(String... value) {
        addEquals(FavoritesMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesMoviesSelection releaseDateNot(String... value) {
        addNotEquals(FavoritesMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesMoviesSelection releaseDateLike(String... value) {
        addLike(FavoritesMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesMoviesSelection releaseDateContains(String... value) {
        addContains(FavoritesMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesMoviesSelection releaseDateStartsWith(String... value) {
        addStartsWith(FavoritesMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesMoviesSelection releaseDateEndsWith(String... value) {
        addEndsWith(FavoritesMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesMoviesSelection orderByReleaseDate(boolean desc) {
        orderBy(FavoritesMoviesColumns.RELEASE_DATE, desc);
        return this;
    }

    public FavoritesMoviesSelection orderByReleaseDate() {
        orderBy(FavoritesMoviesColumns.RELEASE_DATE, false);
        return this;
    }

    public FavoritesMoviesSelection posterPath(String... value) {
        addEquals(FavoritesMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesMoviesSelection posterPathNot(String... value) {
        addNotEquals(FavoritesMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesMoviesSelection posterPathLike(String... value) {
        addLike(FavoritesMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesMoviesSelection posterPathContains(String... value) {
        addContains(FavoritesMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesMoviesSelection posterPathStartsWith(String... value) {
        addStartsWith(FavoritesMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesMoviesSelection posterPathEndsWith(String... value) {
        addEndsWith(FavoritesMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesMoviesSelection orderByPosterPath(boolean desc) {
        orderBy(FavoritesMoviesColumns.POSTER_PATH, desc);
        return this;
    }

    public FavoritesMoviesSelection orderByPosterPath() {
        orderBy(FavoritesMoviesColumns.POSTER_PATH, false);
        return this;
    }

    public FavoritesMoviesSelection voteAverage(String... value) {
        addEquals(FavoritesMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesMoviesSelection voteAverageNot(String... value) {
        addNotEquals(FavoritesMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesMoviesSelection voteAverageLike(String... value) {
        addLike(FavoritesMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesMoviesSelection voteAverageContains(String... value) {
        addContains(FavoritesMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesMoviesSelection voteAverageStartsWith(String... value) {
        addStartsWith(FavoritesMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesMoviesSelection voteAverageEndsWith(String... value) {
        addEndsWith(FavoritesMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesMoviesSelection orderByVoteAverage(boolean desc) {
        orderBy(FavoritesMoviesColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public FavoritesMoviesSelection orderByVoteAverage() {
        orderBy(FavoritesMoviesColumns.VOTE_AVERAGE, false);
        return this;
    }
}
