package com.moviesapp.amrelmasry.popular_movies_app.models;

/**
 * Created by AmrELmasry on 9/24/2015.
 */
public class Movie {

    private String api_id;
    private String overview;
    private String release_date;
    private String poster_path;
    private String popularity;
    private String title;
    private String vote_average;


    public Movie(String api_id, String overview, String release_date, String poster_path, String popularity, String title, String vote_average) {
        this.api_id = api_id;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.title = title;
        this.vote_average = vote_average;
    }
}
