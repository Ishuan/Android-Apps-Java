package com.example.android.moviedb;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchMovies extends AppCompatActivity implements movieAsyncTask.MovieInterface {

    private ListView movieListView;
    private String query;
    private ArrayList<Movie> parsedMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);
        setTitle(R.string.searchmovies);

        Button searchBtn = findViewById(R.id.searchBtn);
        final EditText movieTitle = findViewById(R.id.movieName);
        movieListView = findViewById(R.id.movieListView);

        final String apiKey = "185117c172d0391cb9c3e18aa1f122b8";
        final String url = "https://api.themoviedb.org/3/search/movie";

        if (isConnected()) {
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    query = movieTitle.getText().toString();
                    if (query.length() == 0)
                        Toast.makeText(SearchMovies.this, "Please Enter Movie Name"
                                , Toast.LENGTH_SHORT).show();
                    else
                        new movieAsyncTask(SearchMovies.this, SearchMovies.this)
                                .execute(url + "?query=" + query + "&api_key=" + apiKey + "&page=1");
                }
            });
        } else
            Toast.makeText(SearchMovies.this, "No Internet", Toast.LENGTH_SHORT).show();
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected())
            return false;
        else
            return true;
    }

    @Override
    public void sendParsedMovies(final ArrayList<Movie> sendMovieList) {
        parsedMovieList = sendMovieList;
        if (parsedMovieList.size() == 0) {
            Toast.makeText(SearchMovies.this, "No Result", Toast.LENGTH_SHORT).show();
            movieListView.setAdapter(null);
        } else {
            MovieAdapter movieAdapter = new MovieAdapter(SearchMovies.this, android.R.layout.simple_list_item_1, parsedMovieList);
            movieListView.setAdapter(movieAdapter);
            movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (isConnected()) {
                        Intent intent = new Intent(SearchMovies.this, Details.class);
                        intent.putExtra("movie", parsedMovieList.get(i));
                        startActivity(intent);
                    } else
                        Toast.makeText(SearchMovies.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.homeQuit):
                finish();
                return true;
            case (R.id.homeFavorite):
                Intent intent = new Intent(getApplicationContext(), FavoriteMovies.class);
                startActivity(intent);
                return true;
            case (R.id.homeRating):
                Collections.sort(parsedMovieList, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie, Movie t1) {
                        return (Float.compare(t1.getMovieRating(),movie.getMovieRating()));
                    }
                });
                MovieAdapter movieAdapter = new MovieAdapter(SearchMovies.this, android.R.layout.simple_list_item_1, parsedMovieList);
                movieListView.setAdapter(movieAdapter);
                return true;
            case (R.id.homePopularity):
                Collections.sort(parsedMovieList, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie, Movie t1) {
                        return (Float.compare(t1.getMoviePopularity(),movie.getMoviePopularity()));
                    }
                });
                movieAdapter = new MovieAdapter(SearchMovies.this, android.R.layout.simple_list_item_1, parsedMovieList);
                movieListView.setAdapter(movieAdapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
