package com.example.android.newsapp2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity implements NewsAsyncTask.INewsParse {

    private ImageButton prevBtn;
    private ImageButton nextBtn;
    private TextView category;
    private ArrayList<News> recvdNews;
    private TextView newsTitle;
    private TextView newsDate;
    private ImageView newsImage;
    private TextView newsDesc;
    private TextView newsCounter;
    private int newsIndex = 0;

    LinkedHashMap<String, String> newsCategories = new LinkedHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goBtn = findViewById(R.id.goBtn);

        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        category = findViewById(R.id.catText);
        newsTitle = findViewById(R.id.titleText);
        newsDate = findViewById(R.id.newsDate);
        newsImage = findViewById(R.id.newsImage);
        newsDesc = findViewById(R.id.newsDesc);
        newsCounter = findViewById(R.id.newsCounter);

        prevBtn.setEnabled(false);
        nextBtn.setEnabled(false);

        newsCategories.put("Top Stories", "http://rss.cnn.com/rss/cnn_topstories.rss");
        newsCategories.put("World", "http://rss.cnn.com/rss/cnn_world.rss");
        newsCategories.put("U.S.", "http://rss.cnn.com/rss/cnn_us.rss");
        newsCategories.put("Business", "http://rss.cnn.com/rss/money_latest.rss");
        newsCategories.put("Politics", "http://rss.cnn.com/rss/cnn_allpolitics.rss");
        newsCategories.put("Technology", "http://rss.cnn.com/rss/cnn_tech.rss");
        newsCategories.put("Health", "http://rss.cnn.com/rss/cnn_health.rss");
        newsCategories.put("Entertainment", "http://rss.cnn.com/rss/cnn_showbiz.rss");
        newsCategories.put("Travel", "http://rss.cnn.com/rss/cnn_travel.rss");
        newsCategories.put("Living", "http://rss.cnn.com/rss/cnn_living.rss");
        newsCategories.put("Most Recent", "http://rss.cnn.com/rss/cnn_latest.rss");

        final CharSequence[] newsCategoriesArray = newsCategories.keySet().toArray(new CharSequence[newsCategories.keySet().size()]);

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    newsIndex = 0;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.chooseCategory)
                            .setItems(newsCategoriesArray, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    category.setText(newsCategoriesArray[i]);
                                    new NewsAsyncTask(MainActivity.this, MainActivity.this)
                                            .execute(newsCategories.get(category.getText().toString()));
                                }
                            });
                    builder.show();
                } else
                    Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newsIndex == 0)
                    newsIndex = recvdNews.size() - 1;
                else
                    newsIndex--;
                sendParseNews(recvdNews);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newsIndex == recvdNews.size() - 1)
                    newsIndex = 0;
                else
                    newsIndex++;
                sendParseNews(recvdNews);
            }
        });

        newsImage.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(recvdNews.get(newsIndex).getNewsLink()));
                startActivity(intent);
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

    @Override
    public void sendParseNews(ArrayList<News> news) {
        recvdNews = news;

        if (recvdNews.size() > 1) {
            prevBtn.setEnabled(true);
            nextBtn.setEnabled(true);
        }

        newsTitle.setText(recvdNews.get(newsIndex).getNewsTitle());
        newsDate.setText(recvdNews.get(newsIndex).getNewsPubDate());
        Picasso.get().load(recvdNews.get(newsIndex).getNewsImageURL()).into(newsImage);
        newsDesc.setText(recvdNews.get(newsIndex).getNewsDesc());
        newsCounter.setText((newsIndex + 1) + " out of " + recvdNews.size());

    }
}
