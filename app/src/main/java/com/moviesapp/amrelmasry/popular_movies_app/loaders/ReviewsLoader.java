package com.moviesapp.amrelmasry.popular_movies_app.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.moviesapp.amrelmasry.popular_movies_app.models.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmrELmasry on 9/30/2015.
 */
public class ReviewsLoader extends AsyncTaskLoader<List> {


    public ReviewsLoader(Context context) {
        super(context);
    }

    @Override
    public List loadInBackground() {

        ArrayList<Review> reviews = new ArrayList<>();

        reviews.add(new Review("454", "Ahmed", "FJDFJD dfjhdj dfjfhdjhfh"));
        reviews.add(new Review("454", "Rami", "FJDFJD df gfg fg fgjkj gf jhdj dfjfhdjhfh"));
        reviews.add(new Review("454", "Sameh", "FJklk [pwewm mgff DFJD dfjhdj dfjfhdjhfh"));


        return reviews;
    }
}
