package com.example.android.moviedb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        Movie movie = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item,parent,false);
        }

        ImageView movieImage = convertView.findViewById(R.id.listMovieIcon);
        TextView movieTitle = convertView.findViewById(R.id.listMovieTitle);
        TextView movieReleaseDate = convertView.findViewById(R.id.listMovieRelease);

        Picasso.get().load("http://image.tmdb.org/t/p/w154/"+movie.getMoviePosterPath()).into(movieImage);
        movieTitle.setText(movie.getMovieTitle());
        movieReleaseDate.setText(String.format("%s %s", getContext().getResources().getString(R.string.released)
                , movie.getMovieReleaseDate().split("-")[0]));

        return convertView;
    }
}
