package com.example.android.moviedb;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchMovies extends AppCompatActivity implements movieAsyncTask.MovieInterface {

    private ListView movieListView;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);
        setTitle(R.string.searchmovies);

        Button searchBtn = findViewById(R.id.searchBtn);
        Button favBtn = findViewById(R.id.tempFav);
        final EditText movieTitle = findViewById(R.id.movieName);
        movieListView = findViewById(R.id.movieListView);

        final String apiKey = "185117c172d0391cb9c3e18aa1f122b8";
        final String url = "https://api.themoviedb.org/3/search/movie";

        if(isConnected()){
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    query = movieTitle.getText().toString();
                    if(query.length()==0)
                        Toast.makeText(SearchMovies.this,"Please Enter Movie Name"
                                ,Toast.LENGTH_SHORT).show();
                    else
                        new movieAsyncTask(SearchMovies.this,SearchMovies.this)
                                .execute(url+"?query="+query+"&api_key="+apiKey+"&page=1");
                }
            });

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),FavoriteMovies.class);
                    startActivity(intent);
                }
            });
        }else
            Toast.makeText(SearchMovies.this,"No Internet",Toast.LENGTH_SHORT).show();
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

        if (sendMovieList.size() == 0) {
            Toast.makeText(SearchMovies.this, "No Result", Toast.LENGTH_SHORT).show();
            movieListView.setAdapter(null);
        }
        else {
            MovieAdapter movieAdapter = new MovieAdapter(SearchMovies.this, android.R.layout.simple_list_item_1, sendMovieList);
            movieListView.setAdapter(movieAdapter);
            movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (isConnected()) {
                        Intent intent = new Intent(SearchMovies.this, Details.class);
                        intent.putExtra("movie", sendMovieList.get(i));
                        startActivity(intent);
                    } else
                        Toast.makeText(SearchMovies.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
