package com.moviesapp.amrelmasry.popular_movies_app.sync;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.moviesapp.amrelmasry.popular_movies_app.provider.popular.PopularColumns;
import com.moviesapp.amrelmasry.popular_movies_app.provider.popular.PopularContentValues;
import com.moviesapp.amrelmasry.popular_movies_app.provider.popular.PopularSelection;
import com.moviesapp.amrelmasry.popular_movies_app.utilities.ConnectionUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FetchPopularMovies extends AsyncTask<Void, Void, Void> {

    private final Context mContext;
    private final Integer page;
    boolean isInitialFetch;

    public FetchPopularMovies(Context mContext, Integer page, boolean isInitialFetch) {
        this.mContext = mContext;
        this.page = page;
        this.isInitialFetch = isInitialFetch;
    }

    // make api call , bring popular movies and store them in DB by provider
    @Override
    protected Void doInBackground(Void... params) {


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

        String JSONstr = ConnectionUtilities.getJSONString(uri);

        if (JSONstr != null) {
            if (isInitialFetch) {
                // initial fetch , clear database and insert new data
                deletOldDataOnDB();
                insertMoviesIntoDB(JSONstr);
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

                PopularContentValues contentValues = new PopularContentValues();

                contentValues.putMovieApiId(movieObj.getString("id"))
                        .putTitle(movieObj.getString("title"))
                        .putOverview(movieObj.getString("overview"))
                        .putReleaseDate(movieObj.getString("release_date"))
                        .putVoteAverage(movieObj.getString("vote_average"))
                        .putPosterPath(movieObj.getString("poster_path"));

                mContext.getContentResolver().insert(PopularColumns.CONTENT_URI, contentValues.values());


            }
        } catch (JSONException e) {
            Log.e("JSONError", "Couldn't parse JSON String");
        }
    }

    private void deletOldDataOnDB() {

        PopularSelection where = new PopularSelection();
        where.query(mContext);
        where.delete(mContext);

    }


}