package com.example.android.moviedb;

import java.io.Serializable;

public class Movie implements Serializable {

    private String movieTitle;
    private String movieReleaseDate;
    private String movieOverview;
    private Float movieRating;
    private String moviePosterPath;
    private Float moviePopularity;
    private boolean isFav = false;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public Float getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(Float movieRating) {
        this.movieRating = movieRating;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public float getMoviePopularity() {
        return moviePopularity;
    }

    public void setMoviePopularity(float moviePopularity) {
        this.moviePopularity = moviePopularity;
    }

    public boolean getIsFav() {
        return isFav;
    }

    public void setIsFav(boolean fav) {
        isFav = fav;
    }
}
