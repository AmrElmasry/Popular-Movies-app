package com.moviesapp.amrelmasry.popular_movies_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.moviesapp.amrelmasry.popular_movies_app.utilities.GeneralUtilities;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    private String mShowMoviesby;
    private boolean mTwoPane;
    private static final String MOVIE_DETAILS_FRAGMENT_TAG = "MOVIE_DETAILS";

    final String key = "LASTSHOWBY";
    private boolean isChangedWhenDead = false;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(key, mShowMoviesby);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.movie_details_container) != null) {
            // Two pane mode
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_details_container, new MovieDetailsFragment(), MOVIE_DETAILS_FRAGMENT_TAG)
                        .commit();
            }
        } else {
            // one pane mode
            mTwoPane = false;
        }
        mShowMoviesby = GeneralUtilities.getShowMoviesBy(this);

        // check if the activity destroyed and the value has changed
        // useful when rotating device in settings activity and changing the value

        if (savedInstanceState != null) {
            String old = savedInstanceState.getString(key);
            if (old != null && !old.equals(mShowMoviesby)) {
                Log.i("Really", "Something wrong");
                isChangedWhenDead = true;

            }
        }


    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.i("Really", "onResume");

        String showBy = GeneralUtilities.getShowMoviesBy(this);


        if ((showBy != null && !showBy.equals(mShowMoviesby)) || isChangedWhenDead) {

            MainActivityFragment mainf = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.mainActivityFragment);
            if (null != mainf) {
                mainf.onShowByChanged(showBy);
            }
            mShowMoviesby = showBy;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(String movieApiID, Uri contentUri) {

        if (mTwoPane) {

            MovieDetailsFragment f = MovieDetailsFragment.newInstance(movieApiID, contentUri.toString(), this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, f, MOVIE_DETAILS_FRAGMENT_TAG)
                    .commit();

        } else {

            Intent intent = new Intent(this, MovieDetails.class);

            intent.putExtra(getString(R.string.movie_api_id), movieApiID);
            intent.putExtra(getString(R.string.table_Uri), contentUri.toString());

            startActivity(intent);

        }
    }

//    @Override
//    public int scrollToTopVisibility() {
//        if (mTwoPane) {
//            return View.GONE;
//        } else {
//            return View.VISIBLE;
//        }
//    }
}
