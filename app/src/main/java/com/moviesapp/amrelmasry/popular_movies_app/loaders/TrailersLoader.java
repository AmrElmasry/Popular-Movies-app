package com.moviesapp.amrelmasry.popular_movies_app.loaders;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.moviesapp.amrelmasry.popular_movies_app.models.Trailer;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.ConnectionUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmrELmasry on 9/30/2015.
 */
public class TrailersLoader extends AsyncTaskLoader<List> {

    String movieApiId;

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

    public TrailersLoader(Context context, String movieApiId) {
        super(context);
        this.movieApiId = movieApiId;
        Log.i("LoaderUpdate", "Trailers Loader Constructor invoked");

    }

    @Override
    public ArrayList<Trailer> loadInBackground() {
        Log.i("LoaderUpdate", "Get Trailers in Background and return them");

        ArrayList<Trailer> trailers = new ArrayList<>();

        // TODO Remove hard coded uri

        Uri uri = Uri.parse("http://api.themoviedb.org/3/movie/" + movieApiId + "/videos?api_key=27c124869ccb88b1134ed9504b7e38af");
        String jsonString = ConnectionUtilities.getJSONString(uri);

        if (jsonString != null) {
            try {
                JSONObject JSONobj = new JSONObject(jsonString);
                JSONArray trailersArray = JSONobj.getJSONArray("results");

                for (int i = 0; i < trailersArray.length(); i++) {

                    trailers.add(new Trailer(
                            trailersArray.getJSONObject(i).getString("key"),
                            trailersArray.getJSONObject(i).getString("name")));
                }
                return trailers;

            } catch (Exception e) {
                Log.e("JSON", "Erorr parsing JSON");
                return null;

            }
        }


        return null;
    }
}
