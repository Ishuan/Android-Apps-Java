package com.example.android.moviedb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private boolean favFlag = false;
    private ArrayList<Movie> favMovieList  = new ArrayList<>();

    public MovieAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!movie.getIsFav()) {
                    movie.setIsFav(true);
                    Toast.makeText(getContext(), "Movie mark as Favourite", Toast.LENGTH_SHORT).show();
                    favBtn.setImageResource(android.R.drawable.btn_star_big_on);
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("favList", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    favMovieList.add(movie);
                    Gson gson = new Gson();
                    String json  = gson.toJson(favMovieList);
                    editor.putString("movie",json);
                    editor.apply();
                } else {
                    //Un-favourite
                    movie.setIsFav(false);
                    Toast.makeText(getContext(), "Movie mark Un-Favourite", Toast.LENGTH_SHORT).show();
                    favBtn.setImageResource(android.R.drawable.btn_star_big_off);
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("favList", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    favMovieList.remove(movie);
                    Gson gson = new Gson();
                    String json  = gson.toJson(favMovieList);
                    editor.putString("movie",json);
                    editor.apply();

                }
            }
        });
        return convertView;
    }
}
