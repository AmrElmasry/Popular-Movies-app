package com.moviesapp.amrelmasry.popular_movies_app.provider.popular;

import com.moviesapp.amrelmasry.popular_movies_app.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * popular movies.
 */
public interface PopularModel extends BaseModel {

    /**
     * Movie tilte
     * Cannot be {@code null}.
     */
    @NonNull
    String getTitle();

    /**
     * Movie plot
     * Cannot be {@code null}.
     */
    @NonNull
    String getOverview();

    /**
     * Movie release date
     * Cannot be {@code null}.
     */
    @NonNull
    String getReleaseDate();

    /**
     * Movie poster path
     * Cannot be {@code null}.
     */
    @NonNull
    String getPosterPath();

    /**
     * Movie vote average
     * Cannot be {@code null}.
     */
    @NonNull
    String getVoteAverage();
}