package com.moviesapp.amrelmasry.popular_movies_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.moviesapp.amrelmasry.popular_movies_app.utilities.Utilities;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    private String mShowMoviesby;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mShowMoviesby = Utilities.getShowMoviesBy(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        String showBy = Utilities.getShowMoviesBy(this);


        if (showBy != null && !showBy.equals(mShowMoviesby)) {

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
    public void onItemSelected(String movieApiID, String tableName, Uri contentUri) {

        Intent intent = new Intent(this, MovieDetails.class);

        intent.putExtra(getString(R.string.movie_api_id), movieApiID);
        intent.putExtra(getString(R.string.table_name), tableName);
        intent.putExtra(getString(R.string.table_Uri), contentUri.toString());

        startActivity(intent);
    }
}
