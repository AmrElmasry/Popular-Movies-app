package com.moviesapp.amrelmasry.popular_movies_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get intent extras and pass it to fragment
        if (savedInstanceState == null) {

            Log.i("CREATE", "Movie Details Activity - onCreate - receive intent and add fragment");

            MovieDetailsFragment f = MovieDetailsFragment.newInstance(
                    getIntent().getStringExtra(getString(R.string.movie_api_id)),
                    getIntent().getStringExtra(getString(R.string.table_name)),
                    getIntent().getStringExtra(getString(R.string.table_Uri)),
                    this);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_details_container, f)
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
