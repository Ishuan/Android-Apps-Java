package com.example.android.moviedb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private ListView favListView;
    static FavMovieAdapter favMovieAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(R.string.favorite);

        favListView = findViewById(R.id.favListView);

        favMovieList = getMovieList();

        for(Movie mov:favMovieList)
            Log.d("Demo","Data in fav movie List: "+mov.getMovieTitle()+" "+mov.getIsFav());

        favMovieAdapter = new FavMovieAdapter(FavoriteMovies.this, android.R.layout.simple_list_item_1, favMovieList);
        favListView.setAdapter(favMovieAdapter);

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

    private ArrayList<Movie> getMovieList(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("favList", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MovieAdapter.KEY_JSON, "");
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        ArrayList<Movie> movieList = gson.fromJson(json, type);
        return movieList;
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
                if (favMovieList.size() < 1)
                    Toast.makeText(FavoriteMovies.this, "No Movies to Sort", Toast.LENGTH_SHORT).show();
                else {
                    Collections.sort(favMovieList, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie movie, Movie t1) {
                            return (Float.compare(t1.getMovieRating(), movie.getMovieRating()));
                        }
                    });
                    favMovieAdapter = new FavMovieAdapter(FavoriteMovies.this, android.R.layout.simple_list_item_1, favMovieList);
                    favListView.setAdapter(favMovieAdapter);
                }
                return true;
            case R.id.favPopularity:
                if (favMovieList.size() < 1)
                    Toast.makeText(FavoriteMovies.this, "No Movies to Sort", Toast.LENGTH_SHORT).show();
                else {
                    Collections.sort(favMovieList, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie movie, Movie t1) {
                            return (Float.compare(t1.getMoviePopularity(), movie.getMoviePopularity()));
                        }
                    });
                    favMovieAdapter = new FavMovieAdapter(FavoriteMovies.this, android.R.layout.simple_list_item_1, favMovieList);
                    favListView.setAdapter(favMovieAdapter);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}