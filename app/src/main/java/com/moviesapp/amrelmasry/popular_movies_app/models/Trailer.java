package com.moviesapp.amrelmasry.popular_movies_app.models;

import android.net.Uri;

public class Trailer {
    private String key;
    private String name;


    public Trailer(String key, String name) {
        this.key = key;
        this.name = name;
    }


    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }


    public Uri getThumbnailUri() {
        // uri : "http://img.youtube.com/vi/" + getKey() + "/default.jpg"
        final String BASE_URL = "http://img.youtube.com";
        return Uri.parse(BASE_URL).buildUpon().appendPath("vi").appendPath(getKey()).appendPath("default.jpg").build();
    }

    public Uri getUri() {
        final String BASE_URL = "http://www.youtube.com/watch?";
        return Uri.parse(BASE_URL).buildUpon().appendQueryParameter("v", getKey()).build();
    }
}
