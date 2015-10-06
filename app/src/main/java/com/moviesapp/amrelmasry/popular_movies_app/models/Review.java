package com.moviesapp.amrelmasry.popular_movies_app.models;

/**
 * Created by AmrELmasry on 9/30/2015.
 */
public class Review {

    private String author;
    private String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }




    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
