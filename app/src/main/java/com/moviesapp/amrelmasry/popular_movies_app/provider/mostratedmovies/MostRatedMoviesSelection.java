package com.moviesapp.amrelmasry.popular_movies_app.provider.mostratedmovies;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.moviesapp.amrelmasry.popular_movies_app.provider.base.AbstractSelection;

/**
 * Selection for the {@code most_rated_movies} table.
 */
public class MostRatedMoviesSelection extends AbstractSelection<MostRatedMoviesSelection> {
    @Override
    protected Uri baseUri() {
        return MostRatedMoviesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MostRatedMoviesCursor} object, which is positioned before the first entry, or null.
     */
    public MostRatedMoviesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MostRatedMoviesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public MostRatedMoviesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MostRatedMoviesCursor} object, which is positioned before the first entry, or null.
     */
    public MostRatedMoviesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MostRatedMoviesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public MostRatedMoviesCursor query(Context context) {
        return query(context, null);
    }


    public MostRatedMoviesSelection id(long... value) {
        addEquals("most_rated_movies." + MostRatedMoviesColumns._ID, toObjectArray(value));
        return this;
    }

    public MostRatedMoviesSelection idNot(long... value) {
        addNotEquals("most_rated_movies." + MostRatedMoviesColumns._ID, toObjectArray(value));
        return this;
    }

    public MostRatedMoviesSelection orderById(boolean desc) {
        orderBy("most_rated_movies." + MostRatedMoviesColumns._ID, desc);
        return this;
    }

    public MostRatedMoviesSelection orderById() {
        return orderById(false);
    }

    public MostRatedMoviesSelection title(String... value) {
        addEquals(MostRatedMoviesColumns.TITLE, value);
        return this;
    }

    public MostRatedMoviesSelection titleNot(String... value) {
        addNotEquals(MostRatedMoviesColumns.TITLE, value);
        return this;
    }

    public MostRatedMoviesSelection titleLike(String... value) {
        addLike(MostRatedMoviesColumns.TITLE, value);
        return this;
    }

    public MostRatedMoviesSelection titleContains(String... value) {
        addContains(MostRatedMoviesColumns.TITLE, value);
        return this;
    }

    public MostRatedMoviesSelection titleStartsWith(String... value) {
        addStartsWith(MostRatedMoviesColumns.TITLE, value);
        return this;
    }

    public MostRatedMoviesSelection titleEndsWith(String... value) {
        addEndsWith(MostRatedMoviesColumns.TITLE, value);
        return this;
    }

    public MostRatedMoviesSelection orderByTitle(boolean desc) {
        orderBy(MostRatedMoviesColumns.TITLE, desc);
        return this;
    }

    public MostRatedMoviesSelection orderByTitle() {
        orderBy(MostRatedMoviesColumns.TITLE, false);
        return this;
    }

    public MostRatedMoviesSelection apiId(String... value) {
        addEquals(MostRatedMoviesColumns.API_ID, value);
        return this;
    }

    public MostRatedMoviesSelection apiIdNot(String... value) {
        addNotEquals(MostRatedMoviesColumns.API_ID, value);
        return this;
    }

    public MostRatedMoviesSelection apiIdLike(String... value) {
        addLike(MostRatedMoviesColumns.API_ID, value);
        return this;
    }

    public MostRatedMoviesSelection apiIdContains(String... value) {
        addContains(MostRatedMoviesColumns.API_ID, value);
        return this;
    }

    public MostRatedMoviesSelection apiIdStartsWith(String... value) {
        addStartsWith(MostRatedMoviesColumns.API_ID, value);
        return this;
    }

    public MostRatedMoviesSelection apiIdEndsWith(String... value) {
        addEndsWith(MostRatedMoviesColumns.API_ID, value);
        return this;
    }

    public MostRatedMoviesSelection orderByApiId(boolean desc) {
        orderBy(MostRatedMoviesColumns.API_ID, desc);
        return this;
    }

    public MostRatedMoviesSelection orderByApiId() {
        orderBy(MostRatedMoviesColumns.API_ID, false);
        return this;
    }

    public MostRatedMoviesSelection overview(String... value) {
        addEquals(MostRatedMoviesColumns.OVERVIEW, value);
        return this;
    }

    public MostRatedMoviesSelection overviewNot(String... value) {
        addNotEquals(MostRatedMoviesColumns.OVERVIEW, value);
        return this;
    }

    public MostRatedMoviesSelection overviewLike(String... value) {
        addLike(MostRatedMoviesColumns.OVERVIEW, value);
        return this;
    }

    public MostRatedMoviesSelection overviewContains(String... value) {
        addContains(MostRatedMoviesColumns.OVERVIEW, value);
        return this;
    }

    public MostRatedMoviesSelection overviewStartsWith(String... value) {
        addStartsWith(MostRatedMoviesColumns.OVERVIEW, value);
        return this;
    }

    public MostRatedMoviesSelection overviewEndsWith(String... value) {
        addEndsWith(MostRatedMoviesColumns.OVERVIEW, value);
        return this;
    }

    public MostRatedMoviesSelection orderByOverview(boolean desc) {
        orderBy(MostRatedMoviesColumns.OVERVIEW, desc);
        return this;
    }

    public MostRatedMoviesSelection orderByOverview() {
        orderBy(MostRatedMoviesColumns.OVERVIEW, false);
        return this;
    }

    public MostRatedMoviesSelection releaseDate(String... value) {
        addEquals(MostRatedMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MostRatedMoviesSelection releaseDateNot(String... value) {
        addNotEquals(MostRatedMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MostRatedMoviesSelection releaseDateLike(String... value) {
        addLike(MostRatedMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MostRatedMoviesSelection releaseDateContains(String... value) {
        addContains(MostRatedMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MostRatedMoviesSelection releaseDateStartsWith(String... value) {
        addStartsWith(MostRatedMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MostRatedMoviesSelection releaseDateEndsWith(String... value) {
        addEndsWith(MostRatedMoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MostRatedMoviesSelection orderByReleaseDate(boolean desc) {
        orderBy(MostRatedMoviesColumns.RELEASE_DATE, desc);
        return this;
    }

    public MostRatedMoviesSelection orderByReleaseDate() {
        orderBy(MostRatedMoviesColumns.RELEASE_DATE, false);
        return this;
    }

    public MostRatedMoviesSelection posterPath(String... value) {
        addEquals(MostRatedMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MostRatedMoviesSelection posterPathNot(String... value) {
        addNotEquals(MostRatedMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MostRatedMoviesSelection posterPathLike(String... value) {
        addLike(MostRatedMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MostRatedMoviesSelection posterPathContains(String... value) {
        addContains(MostRatedMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MostRatedMoviesSelection posterPathStartsWith(String... value) {
        addStartsWith(MostRatedMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MostRatedMoviesSelection posterPathEndsWith(String... value) {
        addEndsWith(MostRatedMoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MostRatedMoviesSelection orderByPosterPath(boolean desc) {
        orderBy(MostRatedMoviesColumns.POSTER_PATH, desc);
        return this;
    }

    public MostRatedMoviesSelection orderByPosterPath() {
        orderBy(MostRatedMoviesColumns.POSTER_PATH, false);
        return this;
    }

    public MostRatedMoviesSelection voteAverage(String... value) {
        addEquals(MostRatedMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MostRatedMoviesSelection voteAverageNot(String... value) {
        addNotEquals(MostRatedMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MostRatedMoviesSelection voteAverageLike(String... value) {
        addLike(MostRatedMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MostRatedMoviesSelection voteAverageContains(String... value) {
        addContains(MostRatedMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MostRatedMoviesSelection voteAverageStartsWith(String... value) {
        addStartsWith(MostRatedMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MostRatedMoviesSelection voteAverageEndsWith(String... value) {
        addEndsWith(MostRatedMoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MostRatedMoviesSelection orderByVoteAverage(boolean desc) {
        orderBy(MostRatedMoviesColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public MostRatedMoviesSelection orderByVoteAverage() {
        orderBy(MostRatedMoviesColumns.VOTE_AVERAGE, false);
        return this;
    }
}
