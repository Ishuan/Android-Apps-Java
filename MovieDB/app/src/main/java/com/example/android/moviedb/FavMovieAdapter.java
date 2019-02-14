package com.example.android.moviedb;

import android.content.Context;
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

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FavMovieAdapter extends ArrayAdapter<Movie> {

    private List<Movie> favMovieList;

    public FavMovieAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
        favMovieList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Movie favMovie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fav_movie_list, parent, false);
        }

        ImageView favMovieIcon = convertView.findViewById(R.id.favListMovieIcon);
        TextView favMovieTitle = convertView.findViewById(R.id.favListMovieTitle);
        TextView favMovieReleaseDate = convertView.findViewById(R.id.favListMovieRelease);
        final ImageButton favMovieBtn = convertView.findViewById(R.id.favListFavBtn);

        Picasso.get().load("http://image.tmdb.org/t/p/w154/" + favMovie.getMoviePosterPath()).into(favMovieIcon);
        favMovieTitle.setText(favMovie.getMovieTitle());
        favMovieReleaseDate.setText(String.format("%s %s", getContext().getResources().getString(R.string.released)
                , favMovie.getMovieReleaseDate().split("-")[0]));
        favMovieBtn.setImageResource(android.R.drawable.btn_star_big_on);

        favMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favMovieBtn.setImageResource(android.R.drawable.btn_star_big_off);
                Toast.makeText(getContext(), "Movie marked as Un-Favorite", Toast.LENGTH_SHORT).show();
                favMovie.setIsFav(false);
                favMovieList.remove(favMovie);
                updateFavMovieList();
                FavoriteMovies.favMovieAdapter.notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private void updateFavMovieList() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("favList", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favMovieList);
        editor.putString(MovieAdapter.KEY_JSON, json);
        editor.apply();
    }
}