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


public class fetchMoviesTask extends AsyncTask<Void, Void, Void> {

    private final Context mContext;
    private final Integer page;
    boolean isInitialFetch;
    SharedPreferences preferences;

    String mtableName;
    Uri mContentUri;

    public fetchMoviesTask(Context mContext, Integer page, boolean isInitialFetch, String tableName, Uri contentUri) {
        this.mContext = mContext;
        this.page = page;
        this.isInitialFetch = isInitialFetch;
        this.mContentUri = contentUri;
        this.mtableName = tableName;
    }


    private Uri getUri() {


        // TODO BUILD URI PARAMS IN DETAILS
        if (mtableName.equals(MoviesColumns.POPULAR_TABLE_NAME)) {
            final String BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?";

//        String page_num = pageIndex.toString();
            String page_num = page.toString();

            // populariy URL example : "http://api.themoviedb.org/3/discover/movie?page=1&sort_by=popularity.desc&api_key=27c124869ccb88b1134ed9504b7e38af"
            // top rated URL example : "http://api.themoviedb.org/3/movie/top_rated?page=1&api_key=27c124869ccb88b1134ed9504b7e38af"

            String sort_by = "popularity.desc";
            final String api_key = "27c124869ccb88b1134ed9504b7e38af";

            final String PAGE_NUM = "page";
            final String SORT_BY = "sort_by";
            final String API_KEY = "api_key";


//            Uri builtUri = Uri.parse("http://api.themoviedb.org/3/discover/movie?page=" + pageIndex + "&sort_by=popularity.desc&api_key=27c124869ccb88b1134ed9504b7e38af");
            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(PAGE_NUM, page_num)
                    .appendQueryParameter(SORT_BY, sort_by)
                    .appendQueryParameter(API_KEY, api_key)
                    .build();

            return uri;
        } else if (mtableName.equals(MoviesColumns.MOST_RATED_TABLE_NAME)) {

            final String BASE_URL =
                    "http://api.themoviedb.org/3/movie/top_rated?";
            String page_num = page.toString();
            final String api_key = "27c124869ccb88b1134ed9504b7e38af";

            final String PAGE_NUM = "page";
            final String API_KEY = "api_key";


            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(PAGE_NUM, page_num)
                    .appendQueryParameter(API_KEY, api_key)
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
                    Log.i("First_Load", "CASE = 1 ");


                } else {
                    Log.i("First_Load", "CASE = 2 ");

                    Log.i("CURSOR", "SIZE of RETREIVED JSON  " + JSONstr.length());

                    saveLastJSONString(JSONstr);
                    deleteAllDataOnDB();
                    insertMoviesIntoDB(JSONstr);

                }


            } else {
                // scroll fetch
                Log.i("First_Load", "CASE = 3 ");

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


        // TODO Create another method to delete only if first page isn't new

//        PopularMoviesSelection where = new PopularMoviesSelection();
//        PopularMoviesCursor cursor = where.query(mContext);

        Cursor cursor = mContext.getContentResolver().query(mContentUri, null, null, null, null);

        Integer count = cursor.getCount();

        Log.i("CURSOR", "First Cursor size = " + count.toString());


        Integer rowsToDel = count - 20;

        String whereClause =
                String.format(" _id IN ( SELECT DISTINCT _id FROM " + mtableName + " ORDER BY _id DESC LIMIT " + rowsToDel.toString() + "  ) ");


        Integer deletedRows = mContext.getContentResolver().delete(mContentUri,
                whereClause, null);

        Log.i("CURSOR", "Deleted Rows = " + deletedRows.toString());


//        PopularMoviesSelection where3 = new PopularMoviesSelection();
//        PopularMoviesCursor cursor2 = where3.query(mContext);

        Cursor cursor2 = mContext.getContentResolver().query(mContentUri, null, null, null, null);

        Integer count2 = cursor2.getCount();

        Log.i("CURSOR", "Second Cursor size = " + count2.toString());


    }

    private void deleteAllDataOnDB() {

//        PopularSelection where = new PopularSelection();
//        where.query(mContext);
//        where.delete(mContext);
        mContext.getContentResolver().delete(mContentUri, null, null);

    }

    private boolean sameLastJSON(String JSONStr) {


        Log.i("First_Load", "Comparing JSON str : ");
        Log.i("First_Load", "Table is : " + mtableName);


        preferences = mContext.getSharedPreferences("JSON", 0);


        String lastJSONstr = null;
        if (mtableName.equals(MoviesColumns.POPULAR_TABLE_NAME)) {
            Log.i("First_Load", "From Table Popular");
            lastJSONstr = preferences.getString(mContext.getString(R.string.last_popular_movies_json_str), mContext.getString(R.string.last_json_str_default));


        }
        if (mtableName.equals(MoviesColumns.MOST_RATED_TABLE_NAME)) {
            Log.i("First_Load", "From Table Most Rated");
            lastJSONstr = preferences.getString(mContext.getString(R.string.last_highest_movies_rated_json_str), mContext.getString(R.string.last_json_str_default));

        }


        Log.i("CURSOR", "SAME LAST JSON ? = " + (lastJSONstr.equals(JSONStr)));

        Log.i("First_Load", "Result is " + JSONStr.equals(lastJSONstr));
        Log.i("First_Load", "First saved JSON form  IS " + lastJSONstr);
        Log.i("First_Load", "Second  JSON form API  IS " + JSONStr);


        return (JSONStr.equals(lastJSONstr));
    }

    private void saveLastJSONString(String JSONStr) {

        preferences = mContext.getSharedPreferences("JSON", 0);
        SharedPreferences.Editor editor = preferences.edit();

        Log.i("CURSOR", "Size BEFORE Saving " + JSONStr.length() + " - Saved Succesfully");

        if (mtableName.equals(MoviesColumns.POPULAR_TABLE_NAME)) {
            editor.putString(mContext.getString(R.string.last_popular_movies_json_str), JSONStr);
        }
        if (mtableName.equals(MoviesColumns.MOST_RATED_TABLE_NAME)) {
            editor.putString(mContext.getString(R.string.last_highest_movies_rated_json_str), JSONStr);
        }
        Log.i("CURSOR", "Saved Succesfully");
        editor.apply();
    }

//    private boolean firstTimeSaving() {
//        preferences = mContext.getSharedPreferences("JSON", 0);
//        String lastJSONstr = preferences.getString(mContext.getString(R.string.last_json_str), mContext.getString(R.string.last_json_str_default));
//
//        Log.i("CURSOR", lastJSONstr);
//
//        return lastJSONstr.equals(mContext.getString(R.string.last_json_str_default));
//
//    }

}