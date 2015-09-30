package com.moviesapp.amrelmasry.popular_movies_app.models;

public class Trailer {
    private String id;
    private String key;
    private String name;
    private String site;

    public Trailer(String id, String key, String name, String site) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }
}
