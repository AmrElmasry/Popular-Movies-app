package com.moviesapp.amrelmasry.popular_movies_app.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.moviesapp.amrelmasry.popular_movies_app.R;
import com.moviesapp.amrelmasry.popular_movies_app.provider.helper.MoviesColumns;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.ConnectionUtilities;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.DatabaseUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FetchMoviesTask extends AsyncTask<Void, Void, Void> {

    private final Context mContext;
    private final int FIRST_PAGE_MOVIES_COUNT = 20;
    private final int page;
    private boolean isInitialFetch;
    private SharedPreferences preferences;

    private String mtableName;
    private Uri mContentUri;

    public FetchMoviesTask(Context mContext, int page, boolean isInitialFetch, String tableName, Uri contentUri) {
        this.mContext = mContext;
        this.page = page;
        this.isInitialFetch = isInitialFetch;
        this.mContentUri = contentUri;
        this.mtableName = tableName;
    }


    private Uri getUri() {


        if (mtableName.equals(MoviesColumns.POPULAR_TABLE_NAME)) {
            final String BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?";

            String page_num = String.valueOf(page);

            // populariy URL example : "http://api.themoviedb.org/3/discover/movie?page=1&sort_by=popularity.desc&api_key=****"
            // top rated URL example : "http://api.themoviedb.org/3/movie/top_rated?page=1&api_key=****"

            String sort_by = "popularity.desc";

            final String PAGE_NUM = "page";
            final String SORT_BY = "sort_by";


            return Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(PAGE_NUM, page_num)
                    .appendQueryParameter(SORT_BY, sort_by)
                    .appendQueryParameter(ConnectionUtilities.API_QUERY_KEY, ConnectionUtilities.API_KEY)
                    .build();
        } else if (mtableName.equals(MoviesColumns.MOST_RATED_TABLE_NAME)) {

            final String BASE_URL =
                    "http://api.themoviedb.org/3/movie/top_rated?";
            String page_num = String.valueOf(page);

            final String PAGE_NUM = "page";


            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(PAGE_NUM, page_num)
                    .appendQueryParameter(ConnectionUtilities.API_QUERY_KEY, ConnectionUtilities.API_KEY)
                    .build();
            return uri;

        }

        return null;
    }

    // make api call , bring popular movies and store them in DB by provider
    @Override
    protected Void doInBackground(Void... params) {


        Uri uri = getUri();
        String JSONstr = ConnectionUtilities.getJSONString(uri);


        if (JSONstr != null) {
            if (isInitialFetch) {
                // initial fetch , clear database and insert new data

                if (sameLastJSON(JSONstr)) {

                    deleteOldDataOnDB();


                } else {


                    saveLastJSONString(JSONstr);
                    deleteAllDataOnDB();
                    insertMoviesIntoDB(JSONstr);

                }


            } else {
                // scroll fetch

                insertMoviesIntoDB(JSONstr);

            }
        }


        return null;
    }

    private void insertMoviesIntoDB(String JSONstr) {

        try {
            JSONObject JSONobj = new JSONObject(JSONstr);
            JSONArray moviesArray = JSONobj.getJSONArray("results");

            for (int i = 0; i < moviesArray.length(); i++) {

                JSONObject movieObj = moviesArray.getJSONObject(i);


                DatabaseUtilities.insertIntoDatabase(movieObj.getString("id"),
                        movieObj.getString("title"),
                        movieObj.getString("overview"),
                        movieObj.getString("release_date"),
                        movieObj.getString("vote_average"),
                        movieObj.getString("poster_path"),
                        mContentUri,
                        mContext
                );

            }
        } catch (JSONException e) {
            Log.e("JSONError", "Couldn't parse JSON String");
        }
    }

    private void deleteOldDataOnDB() {


        Cursor cursor = mContext.getContentResolver().query(mContentUri, null, null, null, null);

        int count = cursor.getCount();





        int rowsToDel = count - FIRST_PAGE_MOVIES_COUNT;

        String whereClause =
                " _id IN ( SELECT DISTINCT _id FROM " + mtableName + " ORDER BY _id DESC LIMIT " + String.valueOf(rowsToDel) + "  ) ";


        mContext.getContentResolver().delete(mContentUri,
                whereClause, null);


    }

    private void deleteAllDataOnDB() {


        mContext.getContentResolver().delete(mContentUri, null, null);

    }

    private boolean sameLastJSON(String JSONStr) {


        preferences = mContext.getSharedPreferences("JSON", Context.MODE_PRIVATE);


        String lastJSONstr = new String();
        if (mtableName.equals(MoviesColumns.POPULAR_TABLE_NAME)) {
            lastJSONstr = preferences.getString(mContext.getString(R.string.last_popular_movies_json_str), mContext.getString(R.string.last_json_str_default)).trim();


        }
        if (mtableName.equals(MoviesColumns.MOST_RATED_TABLE_NAME)) {
            lastJSONstr = preferences.getString(mContext.getString(R.string.last_highest_movies_rated_json_str), mContext.getString(R.string.last_json_str_default)).trim();

        }


        return (JSONStr.trim().equals(lastJSONstr));
    }

    private void saveLastJSONString(String JSONStr) {


        preferences = mContext.getSharedPreferences("JSON", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();

        if (mtableName.equals(MoviesColumns.POPULAR_TABLE_NAME)) {
            editor.putString(mContext.getString(R.string.last_popular_movies_json_str), JSONStr.trim());
        }
        if (mtableName.equals(MoviesColumns.MOST_RATED_TABLE_NAME)) {
            editor.putString(mContext.getString(R.string.last_highest_movies_rated_json_str), JSONStr.trim());
        }
        editor.apply();
    }


}