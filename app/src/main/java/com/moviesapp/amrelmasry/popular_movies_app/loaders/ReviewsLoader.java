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
    public List loadInBackground() {

        ArrayList<Review> reviews = new ArrayList<>();

        // TODO CHANGE HARD CODED URL
        Uri uri = Uri.parse("http://api.themoviedb.org/3/movie/" + movieApiId + "/reviews?api_key=27c124869ccb88b1134ed9504b7e38af");
        String jsonString = ConnectionUtilities.getJSONString(uri);

        try {
            JSONObject JSONobj = new JSONObject(jsonString);
            JSONArray reviewsArray = JSONobj.getJSONArray("results");


            for (int i = 0; i < reviewsArray.length(); i++) {
                reviews.add(new Review(reviewsArray.getJSONObject(i).getString("id"),
                        reviewsArray.getJSONObject(i).getString("author"),
                        reviewsArray.getJSONObject(i).getString("content")));
            }
        } catch (Exception e) {
            Log.e("JSON", "Erorr parsing JSON");
        }


        return reviews;
    }
}
