package com.moviesapp.amrelmasry.popular_movies_app.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.moviesapp.amrelmasry.popular_movies_app.models.Trailer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmrELmasry on 9/30/2015.
 */
public class TrailersLoader extends AsyncTaskLoader<List> {

    @Override
    public void deliverResult(List data) {

        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i("LoaderUpdate", "Start Loading");

    }

    @Override
    public void onCanceled(List data) {
        super.onCanceled(data);
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    public TrailersLoader(Context context) {
        super(context);
        Log.i("LoaderUpdate", "Trailers Loader Constructor invoked");

    }

    @Override
    public ArrayList<Trailer> loadInBackground() {
        Log.i("LoaderUpdate", "Get Trailers in Background and return them");

        ArrayList<Trailer> trailers = new ArrayList<>();
        trailers.add(new Trailer("1", "5454", "Trailer 1", "Youtube"));
        trailers.add(new Trailer("2", "5454", "Trailer 2", "Youtube"));
        trailers.add(new Trailer("3", "5454", "Trailer 3", "Youtube"));

        return trailers;
    }
}
