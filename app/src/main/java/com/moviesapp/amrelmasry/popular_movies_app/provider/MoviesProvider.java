package com.moviesapp.amrelmasry.popular_movies_app.provider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.moviesapp.amrelmasry.popular_movies_app.BuildConfig;
import com.moviesapp.amrelmasry.popular_movies_app.provider.base.BaseContentProvider;
import com.moviesapp.amrelmasry.popular_movies_app.provider.helper.MoviesColumns;

import java.util.Arrays;

public class MoviesProvider extends BaseContentProvider {
    private static final String TAG = MoviesProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.moviesapp.amrelmasry.popular_movies_app.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_FAVORITES_MOVIES = 0;
    private static final int URI_TYPE_FAVORITES_MOVIES_ID = 1;

    private static final int URI_TYPE_MOST_RATED_MOVIES = 2;
    private static final int URI_TYPE_MOST_RATED_MOVIES_ID = 3;

    private static final int URI_TYPE_POPULAR_MOVIES = 4;
    private static final int URI_TYPE_POPULAR_MOVIES_ID = 5;


    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.FAVORITES_TABLE_NAME, URI_TYPE_FAVORITES_MOVIES);
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.FAVORITES_TABLE_NAME + "/#", URI_TYPE_FAVORITES_MOVIES_ID);
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.MOST_RATED_TABLE_NAME, URI_TYPE_MOST_RATED_MOVIES);
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.MOST_RATED_TABLE_NAME + "/#", URI_TYPE_MOST_RATED_MOVIES_ID);
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.POPULAR_TABLE_NAME, URI_TYPE_POPULAR_MOVIES);
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.POPULAR_TABLE_NAME + "/#", URI_TYPE_POPULAR_MOVIES_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return MoviesSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_FAVORITES_MOVIES:
                return TYPE_CURSOR_DIR + MoviesColumns.FAVORITES_TABLE_NAME;
            case URI_TYPE_FAVORITES_MOVIES_ID:
                return TYPE_CURSOR_ITEM + MoviesColumns.FAVORITES_TABLE_NAME;

            case URI_TYPE_MOST_RATED_MOVIES:
                return TYPE_CURSOR_DIR + MoviesColumns.MOST_RATED_TABLE_NAME;
            case URI_TYPE_MOST_RATED_MOVIES_ID:
                return TYPE_CURSOR_ITEM + MoviesColumns.MOST_RATED_TABLE_NAME;

            case URI_TYPE_POPULAR_MOVIES:
                return TYPE_CURSOR_DIR + MoviesColumns.POPULAR_TABLE_NAME;
            case URI_TYPE_POPULAR_MOVIES_ID:
                return TYPE_CURSOR_ITEM + MoviesColumns.POPULAR_TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG)
            Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG)
            Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_FAVORITES_MOVIES:
            case URI_TYPE_FAVORITES_MOVIES_ID:
                res.table = MoviesColumns.FAVORITES_TABLE_NAME;
                res.idColumn = MoviesColumns._ID;
                res.tablesWithJoins = MoviesColumns.FAVORITES_TABLE_NAME;
                res.orderBy = MoviesColumns.FAVORITES_DEFAULT_ORDER;
                break;

            case URI_TYPE_MOST_RATED_MOVIES:
            case URI_TYPE_MOST_RATED_MOVIES_ID:
                res.table = MoviesColumns.MOST_RATED_TABLE_NAME;
                res.idColumn = MoviesColumns._ID;
                res.tablesWithJoins = MoviesColumns.MOST_RATED_TABLE_NAME;
                res.orderBy = MoviesColumns.MOST_RATED_DEFAULT_ORDER;
                break;

            case URI_TYPE_POPULAR_MOVIES:
            case URI_TYPE_POPULAR_MOVIES_ID:
                res.table = MoviesColumns.POPULAR_TABLE_NAME;
                res.idColumn = MoviesColumns._ID;
                res.tablesWithJoins = MoviesColumns.POPULAR_TABLE_NAME;
                res.orderBy = MoviesColumns.POPULAR_DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_FAVORITES_MOVIES_ID:
            case URI_TYPE_MOST_RATED_MOVIES_ID:
            case URI_TYPE_POPULAR_MOVIES_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
