package com.example.android.moviedb;

import java.io.Serializable;

public class Movie implements Serializable {

    private String movieTitle;
    private String movieReleaseDate;
    private String movieOverview;
    private String movieRating;
    private String moviePosterPath;
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

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public boolean getIsFav() {
        return isFav;
    }

    public void setIsFav(boolean fav) {
        isFav = fav;
    }
}
