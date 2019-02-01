package com.example.android.newsorgapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(R.string.detail);

        int index = getIntent().getExtras().getBundle("newsBundle").getInt("newsIndex");
        ArrayList<News> detailedNewsList =
                (ArrayList<News>) getIntent().getExtras().getBundle("newsBundle").getSerializable("News");

        TextView newsTitle = findViewById(R.id.newsTitleDetail);
        TextView newsDate = findViewById(R.id.newsDateDetail);
        ImageView newsImage = findViewById(R.id.newsImageDetail);
        TextView newsDesc = findViewById(R.id.newsDescDetail);

        if(getIntent().getExtras().getBundle("newsBundle")!= null){
            final News newsObj = detailedNewsList.get(index);
            newsTitle.setText(newsObj.getNewsTitle());
            newsDate.setText(newsObj.getNewsPubDate());
            Picasso.get().load(newsObj.getNewsImageURL()).into(newsImage);
            newsDesc.setText(newsObj.getNewsDesc());

            newsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent netIntent = new Intent();
                    if(isConnected()) {
                        netIntent.setAction(Intent.ACTION_VIEW);
                        netIntent.setData(Uri.parse(newsObj.getNewsLink()));
                        startActivity(netIntent);
                    } else
                        Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected())
            return false;
        else
            return true;
    }
}
