package com.moviesapp.amrelmasry.popular_movies_app.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.moviesapp.amrelmasry.popular_movies_app.BuildConfig;
import com.moviesapp.amrelmasry.popular_movies_app.provider.favoritesmovies.FavoritesMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.mostratedmovies.MostRatedMoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.popularmovies.PopularMoviesColumns;

public class MoviesSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = MoviesSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    private static MoviesSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final MoviesSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_FAVORITES_MOVIES = "CREATE TABLE IF NOT EXISTS "
            + FavoritesMoviesColumns.TABLE_NAME + " ( "
            + FavoritesMoviesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavoritesMoviesColumns.TITLE + " TEXT NOT NULL, "
            + FavoritesMoviesColumns.API_ID + " TEXT NOT NULL, "
            + FavoritesMoviesColumns.OVERVIEW + " TEXT NOT NULL, "
            + FavoritesMoviesColumns.RELEASE_DATE + " TEXT NOT NULL, "
            + FavoritesMoviesColumns.POSTER_PATH + " TEXT NOT NULL, "
            + FavoritesMoviesColumns.VOTE_AVERAGE + " TEXT NOT NULL "
            + " );";

    public static final String SQL_CREATE_TABLE_MOST_RATED_MOVIES = "CREATE TABLE IF NOT EXISTS "
            + MostRatedMoviesColumns.TABLE_NAME + " ( "
            + MostRatedMoviesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MostRatedMoviesColumns.TITLE + " TEXT NOT NULL, "
            + MostRatedMoviesColumns.API_ID + " TEXT NOT NULL, "
            + MostRatedMoviesColumns.OVERVIEW + " TEXT NOT NULL, "
            + MostRatedMoviesColumns.RELEASE_DATE + " TEXT NOT NULL, "
            + MostRatedMoviesColumns.POSTER_PATH + " TEXT NOT NULL, "
            + MostRatedMoviesColumns.VOTE_AVERAGE + " TEXT NOT NULL "
            + " );";

    public static final String SQL_CREATE_TABLE_POPULAR_MOVIES = "CREATE TABLE IF NOT EXISTS "
            + PopularMoviesColumns.TABLE_NAME + " ( "
            + PopularMoviesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PopularMoviesColumns.TITLE + " TEXT NOT NULL, "
            + PopularMoviesColumns.API_ID + " TEXT NOT NULL, "
            + PopularMoviesColumns.OVERVIEW + " TEXT NOT NULL, "
            + PopularMoviesColumns.RELEASE_DATE + " TEXT NOT NULL, "
            + PopularMoviesColumns.POSTER_PATH + " TEXT NOT NULL, "
            + PopularMoviesColumns.VOTE_AVERAGE + " TEXT NOT NULL "
            + " );";

    // @formatter:on

    public static MoviesSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static MoviesSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static MoviesSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new MoviesSQLiteOpenHelper(context);
    }

    private MoviesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new MoviesSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static MoviesSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new MoviesSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MoviesSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new MoviesSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_FAVORITES_MOVIES);
        db.execSQL(SQL_CREATE_TABLE_MOST_RATED_MOVIES);
        db.execSQL(SQL_CREATE_TABLE_POPULAR_MOVIES);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
