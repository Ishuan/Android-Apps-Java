package com.example.android.moviedb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FavoriteMovies extends AppCompatActivity {

    private ArrayList<Movie> favMovieList = new ArrayList<>();
    private   ListView favListView;
    private   MovieAdapter movieAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(R.string.favorite);

        favListView = findViewById(R.id.favListView);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("favList", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("movie", "");
        Type type = new TypeToken<ArrayList<Movie>>() {
        }.getType();
        favMovieList = gson.fromJson(json, type);

        movieAdapter = new MovieAdapter(FavoriteMovies.this, android.R.layout.simple_list_item_1, favMovieList);
        favListView.setAdapter(movieAdapter);

        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isConnected()) {
                    Intent intent = new Intent(FavoriteMovies.this, Details.class);
                    intent.putExtra("movie", favMovieList.get(i));
                    startActivity(intent);
                } else
                    Toast.makeText(FavoriteMovies.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favHome:
                Intent intent = new Intent(getApplicationContext(), SearchMovies.class);
                startActivity(intent);
                return true;
            case R.id.favQuit:
                finishAffinity();
                return true;
            case R.id.favRating:
                Collections.sort(favMovieList, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie, Movie t1) {
                        return (Float.compare(t1.getMovieRating(),movie.getMovieRating()));
                    }
                });
                movieAdapter = new MovieAdapter(FavoriteMovies.this, android.R.layout.simple_list_item_1, favMovieList);
                favListView.setAdapter(movieAdapter);
                return true;
            case R.id.favPopularity:
                Collections.sort(favMovieList, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie, Movie t1) {
                        return (Float.compare(t1.getMoviePopularity(),movie.getMoviePopularity()));
                    }
                });
                movieAdapter = new MovieAdapter(FavoriteMovies.this, android.R.layout.simple_list_item_1, favMovieList);
                favListView.setAdapter(movieAdapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}