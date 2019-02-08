package com.example.android.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle(R.string.details);

        TextView movieTitle = findViewById(R.id.detailMovieTitle);
        ImageView movieImage = findViewById(R.id.detailMovieIcon);
        TextView movieOverview = findViewById(R.id.detailMovieOverview);
        TextView movieRelease = findViewById(R.id.detailMovieReleaseText);
        TextView movieRating = findViewById(R.id.detailMovieRatingText);

        try {
            if (getIntent().getExtras().getSerializable("movie") != null) {
                Movie movie = (Movie) getIntent().getExtras().getSerializable("movie");
                movieTitle.setText(movie.getMovieTitle());
                Picasso.get().load("http://image.tmdb.org/t/p/w342/" + movie.getMoviePosterPath()).into(movieImage);
                movieOverview.setText(String.format("%s %s", getApplicationContext().getResources().getString(R.string.overview),
                        movie.getMovieOverview()));
                movieRelease.setText(movie.getMovieReleaseDate());
                movieRating.setText(String.format("%s %s", movie.getMovieRating()
                        , getApplicationContext().getResources().getString(R.string.ten)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.detailHome):
                Intent intent = new Intent(getApplicationContext(), SearchMovies.class);
                startActivity(intent);
                return true;
            case (R.id.detailQuit):
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}