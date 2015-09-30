package com.moviesapp.amrelmasry.popular_movies_app.models;

/**
 * Created by AmrELmasry on 9/30/2015.
 */
public class Review {

    private String id;
    private String author;
    private String content;

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
