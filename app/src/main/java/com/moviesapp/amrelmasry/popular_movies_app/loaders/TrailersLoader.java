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

    private String movieApiId;


    public TrailersLoader(Context context, String movieApiId) {
        super(context);
        this.movieApiId = movieApiId;
        Log.i("LoaderUpdate", "Trailers Loader Constructor invoked");

    }

    @Override
    public ArrayList<Trailer> loadInBackground() {
        Log.i("LoaderUpdate", "Get Trailers in Background and return them");

        ArrayList<Trailer> trailers = new ArrayList<>();


        final String BASE_URL = "http://api.themoviedb.org/3/movie/";
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(movieApiId).appendPath("videos")
                .appendQueryParameter(ConnectionUtilities.API_QUERY_KEY, ConnectionUtilities.API_KEY)
                .build();
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
