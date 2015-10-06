package com.moviesapp.amrelmasry.popular_movies_app.loaders;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.moviesapp.amrelmasry.popular_movies_app.models.Review;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.ConnectionUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmrELmasry on 9/30/2015.
 */
public class ReviewsLoader extends AsyncTaskLoader<List> {

    private String movieApiId;

    public ReviewsLoader(Context context, String movieApiId) {
        super(context);
        this.movieApiId = movieApiId;
    }

    @Override
    public void deliverResult(List data) {
        super.deliverResult(data);
    }

    @Override
    public List loadInBackground() {

        ArrayList<Review> reviews = new ArrayList<>();


        final String BASE_URL = "http://api.themoviedb.org/3/movie/";

//        Uri uri = Uri.parse(BASE_URL + movieApiId + "/reviews?api_key=*****");

        Uri uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(movieApiId)
                .appendPath("reviews")
                .appendQueryParameter(ConnectionUtilities.API_QUERY_KEY, ConnectionUtilities.API_KEY)
                .build();

        String jsonString = ConnectionUtilities.getJSONString(uri);

        if (jsonString != null) {
            try {
                JSONObject JSONobj = new JSONObject(jsonString);
                JSONArray reviewsArray = JSONobj.getJSONArray("results");


                for (int i = 0; i < reviewsArray.length(); i++) {
                    reviews.add(new Review(
                            reviewsArray.getJSONObject(i).getString("author"),
                            reviewsArray.getJSONObject(i).getString("content")));
                }

                return reviews;

            } catch (Exception e) {
                Log.e("JSON", "Erorr parsing JSON");
                return null;
            }
        } else {
            return null;
        }


    }
}
