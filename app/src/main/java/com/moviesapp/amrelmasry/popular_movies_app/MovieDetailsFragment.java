package com.moviesapp.amrelmasry.popular_movies_app;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviesapp.amrelmasry.popular_movies_app.utilities.Utilities;
public class MovieDetailsFragment extends Fragment {

    String movieApiId;
    String tableName;
    String tableUri;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);


        Bundle arguments = getArguments();
        if (arguments != null) {
            movieApiId = arguments.getString(getString(R.string.movie_api_id));
            tableName = arguments.getString(getString(R.string.table_name));
            tableUri = arguments.getString(getString(R.string.table_Uri));

        }
        Log.i("INTENT", "received 2 :" + movieApiId);
        Log.i("INTENT", "received 2 :" + tableName);
        Log.i("INTENT", "received 2 :" + tableUri);

        if (tableName != null) {
            String selection = tableName + ".api_id = ? ";
            String[] selectionArgs = new String[]{movieApiId};
            Cursor cursor = getActivity().getContentResolver().query(Uri.parse(tableUri), null, selection, selectionArgs, null);


            cursor.moveToNext();


            Log.i("INTENT", cursor.getString(Utilities.COL_TITLE));
            cursor.close();
        }
        return rootView;
    }

    public static MovieDetailsFragment newInstance(String movieApiId, String tableName, String tableUri, Context context) {
        MovieDetailsFragment f = new MovieDetailsFragment();

        Bundle arguments = new Bundle();
        arguments.putString(context.getString(R.string.movie_api_id), movieApiId);

        Log.i("INTENT", "Sent :" + movieApiId);

        arguments.putString(context.getString(R.string.table_name), tableName);
        arguments.putString(context.getString(R.string.table_Uri), tableUri);

        f.setArguments(arguments);


        return f;
    }

//    public int getArgs() {
//        return getArguments().getInt("index", 0);
//    }
}
