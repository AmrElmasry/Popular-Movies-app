package com.moviesapp.amrelmasry.popular_movies_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviesapp.amrelmasry.popular_movies_app.provider.popular.PopularCursor;
import com.moviesapp.amrelmasry.popular_movies_app.provider.popular.PopularSelection;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Intent intent = getActivity().getIntent();  // TODO get intent in Activity not in fragment

        String fetchType = intent.getStringExtra(getString(R.string.fetch_type));
        String movieApiId = intent.getStringExtra(getString(R.string.movie_api_id));


        Cursor cursor = null;

        if (fetchType.equals("FETCH_POPULAR")) // TODO edit later from resources
        {

            PopularSelection where = new PopularSelection();
            PopularSelection where2 = new PopularSelection();

            where.movieApiId(movieApiId);
            where2.movieApiId(movieApiId);

            PopularCursor newCursor1 = where.query(getActivity());
            PopularCursor newCursor2 = where.query(getActivity());

            newCursor1.moveToNext();
            newCursor2.moveToNext();

            if (newCursor1 == newCursor2) {
                Log.i("CURSOR", "Equals");
                Log.i("CURSOR", newCursor1.getTitle());
                Log.i("CURSOR", newCursor2.getTitle());

            } else {
                Log.i("CURSOR", "NOT Equals");
                Log.i("CURSOR", newCursor1.getTitle());
                Log.i("CURSOR", newCursor2.getTitle());

            }

            cursor = newCursor1;


        }

        if (cursor != null) {
            Log.i("CURSOR", cursor.getString(1));  // TODO make 1 from
//            cursor.close(); // TODO close or not?
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
