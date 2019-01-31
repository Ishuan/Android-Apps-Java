package com.example.android.newsorgapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        News news = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);
        }

        TextView newsTitle = convertView.findViewById(R.id.newsHeading);
        TextView newsDate = convertView.findViewById(R.id.newsDate);
        ImageView newsImage = convertView.findViewById(R.id.newsImage);

        newsTitle.setText(news.getNewsTitle());
        newsDate.setText(news.getNewsPubDate());
        Picasso.get().load(news.getNewsImageURL()).into(newsImage);

        return convertView;
    }
}
