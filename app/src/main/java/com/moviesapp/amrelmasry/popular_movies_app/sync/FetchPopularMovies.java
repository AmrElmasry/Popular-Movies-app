package com.moviesapp.amrelmasry.popular_movies_app.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.moviesapp.amrelmasry.popular_movies_app.provider.popular.PopularColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.popular.PopularContentValues;


public class FetchPopularMovies extends AsyncTask<Void, Void, Void> {

    private final Context mContext;

    public FetchPopularMovies(Context mContext) {
        this.mContext = mContext;
    }

    // make api call , bring popular movies and store them in DB by provider
    @Override
    protected Void doInBackground(Void... params) {

        // insert mock data
        PopularContentValues contentValues = new PopularContentValues();

        contentValues.putTitle("The movie");
        contentValues.putOverview("Overview");
        contentValues.putReleaseDate("11/11/2015");
        contentValues.putVoteAverage("5.5");
        contentValues.putPosterPath("/kqjL17yufvn9OVLyXYpvtyrFfak.jpg");
        mContext.getContentResolver().insert(PopularColumns.CONTENT_URI, contentValues.values());

        return null;
    }
}
