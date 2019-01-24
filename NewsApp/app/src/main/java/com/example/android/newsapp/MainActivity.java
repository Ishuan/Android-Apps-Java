package com.example.android.newsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAsyncTask.NewsInterface {

    private final CharSequence[] newsCategories =
            {"business", "entertainment", "general", "health", "science", "sports", "technology"};
    private ArrayList<News> receivedNews = null;

    private TextView newsTitle;
    private TextView newsDate;
    private ImageView newsImage;
    private TextView newsDesc;
    private TextView newsCounter;
    private ImageButton prevBtn;
    private ImageButton nextBtn;
    private int newsIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goBtn = findViewById(R.id.goBtn);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        newsTitle = findViewById(R.id.titleText);
        newsDate = findViewById(R.id.newsDate);
        newsImage = findViewById(R.id.newsImage);
        newsDesc = findViewById(R.id.newsDesc);
        newsCounter = findViewById(R.id.newsCounter);
        final TextView newsCatText = findViewById(R.id.catText);

        prevBtn.setEnabled(false);
        nextBtn.setEnabled(false);

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsIndex = 0;
                if (isConnected()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.chooseCategory)
                            .setItems(newsCategories, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    newsCatText.setText(newsCategories[i]);
                                    new NewsAsyncTask(MainActivity.this, MainActivity.this).execute(("https://newsapi.org/v2/top-headlines?country=us&apiKey=d5da01439a5d42b0b1e1cf07533d342f&category=" +
                                            newsCategories[i]));
                                }
                            });
                    builder.show();
                } else
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (newsIndex == receivedNews.size() - 1)
                    newsIndex = 0;
                else
                    newsIndex++;
                sendNewsData(receivedNews);
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newsIndex <= 0)
                    newsIndex = receivedNews.size() - 1;
                else
                    newsIndex--;
                new NewsAsyncTask(MainActivity.this, MainActivity.this).execute(("https://newsapi.org/v2/top-headlines?country=in&apiKey=d5da01439a5d42b0b1e1cf07533d342f&category=" +
                        newsCatText.getText().toString()));
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    @Override
    public void sendNewsData(ArrayList<News> sendNewsList) {
        receivedNews = sendNewsList;

        if (receivedNews.size() == 0)
            Toast.makeText(getApplicationContext(), "There is no news for this category", Toast.LENGTH_SHORT).show();
        else {

            if (receivedNews.size() > 1) {
                prevBtn.setEnabled(true);
                nextBtn.setEnabled(true);
            }
            newsTitle.setText(receivedNews.get(newsIndex).getNewsTitle());
            newsDate.setText(receivedNews.get(newsIndex).getNewsPubDate());
            Picasso.get().load(receivedNews.get(newsIndex).getNewsImageURL()).into(newsImage);
            newsDesc.setText(receivedNews.get(newsIndex).getNewsDesc());
            newsCounter.setText((newsIndex + 1) + " out of " + (receivedNews.size()));
        }
    }
}
