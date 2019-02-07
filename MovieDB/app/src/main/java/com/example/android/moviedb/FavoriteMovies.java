package com.example.android.moviedb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavoriteMovies extends AppCompatActivity {

    private ArrayList<Movie> favMovieList = new ArrayList<>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(R.string.favorite);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("favList",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("movie","");
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        favMovieList = gson.fromJson(json,type);

        MovieAdapter movieAdapter = new MovieAdapter(FavoriteMovies.this,android.R.layout.simple_list_item_1,favMovieList);
        ListView favListView = findViewById(R.id.favListView);
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
}
