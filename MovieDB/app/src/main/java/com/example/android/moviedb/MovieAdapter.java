package com.example.android.moviedb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.content.ContextCompat.startActivity;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private ArrayList<Movie> favMovieList = new ArrayList<>();
    public static final String KEY_JSON = "movie";

    public MovieAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item, parent, false);
        }

        ImageView movieImage = convertView.findViewById(R.id.listMovieIcon);
        final TextView movieTitle = convertView.findViewById(R.id.listMovieTitle);
        TextView movieReleaseDate = convertView.findViewById(R.id.listMovieRelease);
        final ImageButton favBtn = convertView.findViewById(R.id.favBtn);

        Picasso.get().load("http://image.tmdb.org/t/p/w154/" + movie.getMoviePosterPath()).into(movieImage);
        movieTitle.setText(movie.getMovieTitle());
        movieReleaseDate.setText(String.format("%s %s", getContext().getResources().getString(R.string.released)
                , movie.getMovieReleaseDate().split("-")[0]));

        if (movie.getIsFav())
            favBtn.setImageResource(android.R.drawable.btn_star_big_on);
        else
            favBtn.setImageResource(android.R.drawable.btn_star_big_off);

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!movie.getIsFav()) {
                    movie.setIsFav(true);
                    favBtn.setImageResource(android.R.drawable.btn_star_big_on);
                    Toast.makeText(getContext(), "Movie marked Favourite", Toast.LENGTH_SHORT).show();
                    markMovieFav(true, movie);
                } else {
                    movie.setIsFav(false);
                    favBtn.setImageResource(android.R.drawable.btn_star_big_off);
                    Toast.makeText(getContext(), "Movie marked Un-favorite", Toast.LENGTH_SHORT).show();
                    markMovieFav(false, movie);
                }
            }
        });
        return convertView;
    }

    private void markMovieFav(boolean favFlag, Movie favMov) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("favList", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (favFlag)
            favMovieList.add(favMov);
        else
            favMovieList.remove(favMov);
        Gson gson = new Gson();
        String json = gson.toJson(favMovieList);
        editor.putString(KEY_JSON, json);
        editor.apply();
    }
}